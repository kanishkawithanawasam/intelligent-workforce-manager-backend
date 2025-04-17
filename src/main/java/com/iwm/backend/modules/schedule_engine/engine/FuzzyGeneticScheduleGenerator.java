package com.iwm.backend.modules.schedule_engine.engine;

import com.iwm.backend.modules.schedule_engine.configurations.FGAConfigs;
import com.iwm.backend.modules.schedule_engine.models.Employee;
import com.iwm.backend.modules.schedule_engine.models.Population;
import com.iwm.backend.modules.schedule_engine.models.ShiftGO;
import com.iwm.backend.modules.schedule_engine.models.WeeklySchedule;
import com.iwm.backend.modules.schedule_engine.util.CalculationsUtility;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Implements a Fuzzy Genetic Algorithm (FGA) for generating optimised employee schedules.
 *
 * <p>This class combines the traditional Genetic Algorithm (GA) framework with fuzzy logic
 * to dynamically adjust hyperparameters such as mutation rate—based on population diversity
 * and fitness variance. It supports end-to-end evolution processes including:</p>
 *
 * <ul>
 *     <li>Population initialisation</li>
 *     <li>Fitness evaluation</li>
 *     <li>Selection (tournament-based)</li>
 *     <li>Crossover (single-point)</li>
 *     <li>Mutation (adaptive via fuzzy rules)</li>
 *     <li>Survivor selection and elitism</li>
 * </ul>
 *
 * <p>The goal is to evolve weekly employee schedules that minimize cost and constraint violations
 * while aligning with individual employee preferences.</p>
 *
 * @author Kanishka Withanawasam
 * @version 1.0
 */
public class FuzzyGeneticScheduleGenerator{

    private final List<Employee> employees;
    private final Map<LocalDate,Map<Integer,Integer>> demand;
    private double MUTATION_RATE;

    public FuzzyGeneticScheduleGenerator(List<Employee> employees,Map<LocalDate,Map<Integer,Integer>> demand) {
        this.employees = employees;
        this.demand = demand;
    }

    /**
     * Runs the genetic algorithm to generate the optimal weekly schedule.
     *
     * <p>This method initialises a population of schedules and evolves it over a number
     * of iterations using selection, crossover, and mutation. The fitness of each schedule
     * is evaluated using a custom fitness function that balances cost, constraint violations,
     * and employee preference deviations.</p>
     *
     * <p>Fuzzy adaptive mutation is applied based on population diversity and variance to
     * maintain exploration and avoid premature convergence.</p>
     *
     * @return the most optimal schedule found after evolution
     */
    public WeeklySchedule genSchedule() throws IOException {

        // Generates the initial population
        Population population = new Population(employees, demand);

        // Calculates fitness of each schedule in the population.
        double bestAccOnPopulation = 0;
        for(WeeklySchedule weeklySchedule : population.getPopulation()){
            weeklySchedule.setFitnessScore(this.getFitnessScore(weeklySchedule));
            if (weeklySchedule.getFitnessScore() > bestAccOnPopulation){
                bestAccOnPopulation=weeklySchedule.getFitnessScore();
            }
        }

        // Runs the genetic algorithm for the configured number of iterations.
        double accuracy = 0;
        for (int i = 0; i < FGAConfigs.NUMBER_OF_ITERATIONS; i++) {

            // Adapts mutation rate based on population diversity & fitness variance.
            adjustFuzzyParameters(population);

            // Selects parents using tournament selection.
            WeeklySchedule parent1 = tournamentSelection(population);
            WeeklySchedule parent2 = tournamentSelection(population);

            // Performs crossover to produce offspring.
            List<WeeklySchedule> offspringWeeklySchedules = crossover(parent1,parent2);

            // Mutates offsprings
            for(WeeklySchedule weeklySchedule : offspringWeeklySchedules){
                mutate(weeklySchedule,employees);
            }

            // Calculates fitness scores.
            for(WeeklySchedule weeklySchedule : offspringWeeklySchedules){
                weeklySchedule.setFitnessScore(this.getFitnessScore(weeklySchedule));
                if (weeklySchedule.getFitnessScore() > accuracy){
                    accuracy=weeklySchedule.getFitnessScore();
                }
            }

            // Selects survivors to form the next generation.
            population.setPopulation(selectSurvivors(population.getPopulation(), offspringWeeklySchedules));
        }

        // Return the best schedule found in the final generation.
        return calculateAndConfigure(population.getPopulation());
    }

    /**
     * Identifies and returns the best schedule from the given population based on fitness score.
     *
     * <p>The schedule with the highest fitness value is considered the best,
     * representing the most optimal solution found so far in the current generation.</p>
     *
     * @param population the list of candidate weekly schedules
     * @return the schedule with the highest fitness score
     */
    private WeeklySchedule calculateAndConfigure(List<WeeklySchedule> population) {
        return Collections.max(population, Comparator.comparingDouble(WeeklySchedule::getFitnessScore));
    }


    /**
     * Selects the next generation population by combining the best individuals
     * from the old population and newly generated offspring.
     *
     * <p>This method applies an elitist selection strategy where all but the bottom two
     * individuals from the old population are retained, and all offspring are added to form
     * the next generation. It assumes the offspring list size is exactly two to maintain
     * population size.</p>
     *
     * @param oldPopulation the existing population of schedules (sorted by fitness)
     * @param offspring the newly generated schedules via crossover and mutation
     * @return the new population consisting of the top survivors and new offspring
     * @throws RuntimeException if the resulting population size does not match the original
     */
    private List<WeeklySchedule> selectSurvivors(List<WeeklySchedule> oldPopulation, List<WeeklySchedule> offspring) {

        // Sorts both old and new populations by descending fitness
        oldPopulation.sort(Comparator.comparingDouble(s -> -s.getFitnessScore()));
        offspring.sort(Comparator.comparingDouble(s -> -s.getFitnessScore()));

        List<WeeklySchedule> newPopulation = new ArrayList<>();

        // Retains top individuals from old population (excluding last 2)
        newPopulation.addAll(oldPopulation.subList(0, oldPopulation.size()-2));

        // Adds all new offspring to form the new population
        newPopulation.addAll(offspring);

        if (newPopulation.size() != oldPopulation.size()) {
            throw new RuntimeException("New population size is " + newPopulation.size()
            + " but old population size is " + oldPopulation.size());
        }
        return newPopulation;
    }

    /**
     * Applies mutation to a given weekly schedule by randomly reassigning an employee
     * to one of the existing shifts, based on a specified mutation probability.
     *
     * <p>This method helps maintain genetic diversity in the population by introducing
     * small, random changes to individuals. A shift is selected at random, and its assigned
     * employee is replaced with another randomly chosen, valid employee who has not already
     * been assigned to a shift on the same day.</p>
     *
     * @param weeklySchedule the schedule to mutate
     * @param employees the list of all available employees
     */
    private void mutate(WeeklySchedule weeklySchedule, List<Employee> employees) {
        Random rand = new Random();

        // Performs mutation based on mutation rate
        if (rand.nextDouble() < MUTATION_RATE) {

            // Selects a random shift to mutate
            int index = rand.nextInt(weeklySchedule.getShifts().size());
            ShiftGO shiftToMutate = weeklySchedule.getShifts().get(index);

            LocalDate date = shiftToMutate.getDate();
            Employee newEmployee;

            // Chooses a new employee who is not the current one and not already assigned on that date
            do {
                newEmployee = employees.get(rand.nextInt(employees.size()));
            } while (
                    newEmployee.getId() == shiftToMutate.getEmployee().getId() ||
                            weeklySchedule.getEmpDateMap().get(date).contains(newEmployee)
            );

            // Reassigns the shift to the new employee
            shiftToMutate.setEmployee(newEmployee);
        }
    }



    /**
     * Performs single-point crossover on two parent schedules to produce offspring schedules.
     *
     * <p>This method selects a random crossover point and creates two offspring:
     * <ul>
     *   <li>Offspring 1: first part from {@code parent1}, second part from {@code parent2}</li>
     *   <li>Offspring 2: first part from {@code parent2}, second part from {@code parent1}</li>
     * </ul>
     *
     * <p>The crossover mimics biological recombination by exchanging parts of the
     * parents' genomes (shift lists), promoting genetic diversity in the population.</p>
     *
     * @param parent1 the first parent schedule
     * @param parent2 the second parent schedule
     * @return a list containing two offspring schedules
     */
    private List<WeeklySchedule> crossover(WeeklySchedule parent1, WeeklySchedule parent2){
        Random random = new Random();
        int crossoverIndex = random.nextInt(parent1.getShifts().size());

        List<WeeklySchedule> offspringWeeklySchedules = new ArrayList<>();

        // Defines offsprings
        WeeklySchedule offspring1 = new WeeklySchedule();
        WeeklySchedule offspring2 = new WeeklySchedule();

        // Copies first part from each parent to respective offspring
        for (int i = 0; i < crossoverIndex; i++) {
            offspring1.addShift(parent1.getShifts().get(i));
            offspring2.addShift(parent2.getShifts().get(i));
        }

        // Copies remaining part from opposite parent
        for (int i = crossoverIndex; i < parent2.getShifts().size(); i++) {
            offspring1.addShift(parent2.getShifts().get(i));
            offspring2.addShift(parent1.getShifts().get(i));
        }

        offspringWeeklySchedules.add(offspring1);
        offspringWeeklySchedules.add(offspring2);
        return offspringWeeklySchedules;
    }


    /**
     * Performs tournament selection on the given population to select a parent schedule.
     *
     * <p>Tournament selection is a parent selection method in genetic algorithms where
     * {@code tournamentSize} individuals are randomly selected from the population, and
     * the one with the highest fitness score is chosen as the winner.</p>
     *
     * <p>This method maintains a balance between exploration and exploitation by
     * allowing weaker individuals a small chance of selection while still favoring
     * the fittest candidates.</p>
     *
     * @param population the population of schedules to select from
     * @return the best schedule among the randomly selected tournament participants
     */
    private WeeklySchedule tournamentSelection(Population population) {
        Random rand = new Random();
        List<WeeklySchedule> tournament = new ArrayList<>();

        // Randomly selects `tournamentSize` schedules
        for (int i = 0; i < FGAConfigs.TOURNAMENT_SIZE; i++) {
            WeeklySchedule randomWeeklySchedule = population.getPopulation().get(rand.nextInt(population.getPopulation().size()));
            tournament.add(randomWeeklySchedule);
        }

        // Returns the best schedule from the tournament
        return Collections.max(tournament, Comparator.comparing(WeeklySchedule::getFitnessScore));
    }


    /**
     * Dynamically adjusts the mutation rate based on population diversity and fitness variance.
     *
     * <p>This fuzzy logic approach helps balance exploration and exploitation in the genetic algorithm.
     * The method evaluates the diversity and variance of the current population and updates the
     * mutation rate accordingly:</p>
     *
     * <ul>
     *   <li>If both diversity and variance are low → increase mutation rate (to escape local optima)</li>
     *   <li>If both diversity and variance are high → reduce mutation rate (to exploit good solutions)</li>
     *   <li>Otherwise → apply a moderate mutation rate</li>
     * </ul>
     *
     * @param population the current population used to assess diversity and variance
     */
    private void adjustFuzzyParameters(Population population) {
        double diversity = calculateDiversity(population);
        double fitnessVariance = calculateFitnessVariance(population);

        // High convergence (low diversity and variance): encourage exploration.
        if (diversity < 0.05 && fitnessVariance < 0.1) {
            MUTATION_RATE = 0.3;

        }
        // High diversity and variance: encourage exploitation.
        else if (diversity > 0.3 && fitnessVariance > 0.2) {
            MUTATION_RATE = 0.1;
        }
        // Moderate case: maintain balanced mutation.
        else {
            MUTATION_RATE = 0.2;
        }
    }


    /**
     * Calculates the diversity of a population based on the mean absolute deviation
     * from the average fitness score.
     *
     * <p>This method computes how far, on average, each individual's fitness score deviates
     * from the mean fitness of the population. A higher value indicates greater diversity,
     * suggesting the population contains a wide range of solutions. A lower value suggests
     * convergence or lack of variation within the population.</p>
     *
     * @param population the population of weekly schedules to evaluate
     * @return the average absolute deviation of fitness scores from the population mean
     */
    private double calculateDiversity(Population population) {

        // Calculate average fitness
        double avgFitness = population.getPopulation().stream().mapToDouble(WeeklySchedule::getFitnessScore)
                .average().orElse(1);

        // Calculates average absolute deviation
        return population.getPopulation().stream()
                .mapToDouble(s -> Math.abs(s.getFitnessScore() - avgFitness))
                .sum() / population.getPopulation().size();
    }


    /**
     * Calculates the fitness standard deviation of a given population.
     *
     * <p>This metric indicates the diversity of solutions within the population.
     * A higher value implies greater variation in schedule quality (fitness),
     * whereas a lower value may signal convergence (possibly premature).</p>
     *
     * @param population the population of weekly schedules to analyse
     * @return the standard deviation of fitness scores in the population
     */
    private double calculateFitnessVariance(Population population) {

        // Calculate average fitness
        double avgFitness = population.getPopulation().stream()
                .mapToDouble(WeeklySchedule::getFitnessScore).average().orElse(1);

        // Compute variance
        double variance = population.getPopulation().stream()
                .mapToDouble(s -> Math.pow(s.getFitnessScore() - avgFitness, 2))
                .sum() / population.getPopulation().size();

        // Standard deviation
        return Math.sqrt(variance);
    }


    /**
     * Calculates the fitness score for a given weekly schedule.
     *
     * <p>The fitness score is inversely proportional to the sum of:
     * <ul>
     *     <li>Total staffing cost</li>
     *     <liTotal constraint violation penalties (e.g., max hours, daily shift limits)</li>
     *     <li>Total deviation from employee hour preferences</li>
     * </ul>
     *
     * <p>An additional constant {@code +1} is added in the denominator to avoid
     * division by zero. A lower total penalty results in a higher fitness score.</p>
     *
     * @param weeklySchedule the schedule to evaluate
     * @return a fitness score where higher values indicate better quality
     */
    private double getFitnessScore(WeeklySchedule weeklySchedule){

        // Calculates total penalties
        double totalPenalty = (getTotalCost(weeklySchedule)+getTotalViolation(weeklySchedule)+
                getTotalDeviation(weeklySchedule)+1);

        return 1 / totalPenalty;
    }

    /**
     * Calculates the total staffing cost of the given weekly schedule.
     *
     * <p>The total cost is computed by summing the individual cost of each shift,
     * which is determined by the assigned employee's hourly or per-shift cost.</p>
     *
     * @param weeklySchedule the weekly schedule containing all shifts
     * @return the total cost of the schedule
     */
    private double getTotalCost(WeeklySchedule weeklySchedule){

        double cost = 0;

        for (ShiftGO shift : weeklySchedule.getShifts()) {
            cost+=shift.getEmployee().getCost();
        }

        return cost;
    }

    /**
     * Calculates the total deviation between scheduled hours and employee hour preferences
     * for a given weekly schedule.
     *
     * <p>The deviation is computed as the sum of absolute differences between the total number
     * of hours each employee is scheduled to work and their preferred number of weekly hours.
     * This metric is used to measure how well the schedule aligns with employee preferences.</p>
     *
     * @param weeklySchedule the weekly schedule containing all employee shifts
     * @return the total deviation across all employees
     */
    private double getTotalDeviation(WeeklySchedule weeklySchedule){
        double deviation = 0;

        // Get total hours worked by each employee in the schedule
        Map<Employee,Double> totalWeeklyHours = CalculationsUtility
                .countTotalHours(weeklySchedule.getShifts());

        // Sum the absolute deviation from each employee's hour preference
        for (Employee employee : totalWeeklyHours.keySet()) {
            deviation+=Math.abs(totalWeeklyHours.get(employee)-employee.getHoursPreference());
        }
        return deviation;
    }

    /**
     * Calculates the total constraint violation penalty for a weekly schedule.
     *
     * <p>Two types of violations are considered:</p>
     * <ul>
     *     <li><b>Daily ShiftGO Conflict:</b> An employee assigned to more than one shift on the same day (penalty: 5000).</li>
     *     <li><b>Weekly Hour Limit Exceeded:</b> An employee works more than their allowed weekly hours (penalty: 500).</li>
     * </ul>
     *
     * @param weeklySchedule the schedule for the week
     * @return the total violation penalty score
     */
    private int getTotalViolation(WeeklySchedule weeklySchedule){

        int violation = 0;

        // Tracks the number of shifts assigned to each employee per date
        Map<Employee, HashMap<LocalDate, Integer>> dateEmployeeMap = new HashMap<>();

        // Populates the dateEmployeeMap with shift counts per day per employee
        for (ShiftGO shift : weeklySchedule.getShifts()) {
            Employee employee = shift.getEmployee();

            // Adds the employee and date count to the map if it doesn't contain already
            if(!dateEmployeeMap.containsKey(employee)){
                HashMap<LocalDate, Integer> tempMap = new HashMap<>();
                tempMap.put(shift.getDate(), 1);
                dateEmployeeMap.put(employee, tempMap);
            }else{
                dateEmployeeMap.get(employee).put(
                        shift.getDate(),
                        dateEmployeeMap.get(employee).getOrDefault(shift.getDate(), 0)+1);
            }
        }

        // Penalise multiple shifts per day
        for(Employee employee : dateEmployeeMap.keySet()){
            for (LocalDate date : dateEmployeeMap.get(employee).keySet()) {
                if(dateEmployeeMap.get(employee).get(date)>1){
                    violation+=FGAConfigs.DAILY_HOURS_VIOLATION_TOURNAMENT;
                }
            }
        }

        // Checks for employees exceeding their weekly hours limit.
        Map<Employee,Double> totalWeeklyHours = CalculationsUtility.countTotalHours(weeklySchedule.getShifts());
        for (Employee employee : totalWeeklyHours.keySet()) {
            if (totalWeeklyHours.get(employee)>employee.getMaxHoursPerWeek()) {
                violation+=FGAConfigs.WEEKLY_HOURS_VIOLATION_PENALTY;
            }
        }

        return violation;
    }

}