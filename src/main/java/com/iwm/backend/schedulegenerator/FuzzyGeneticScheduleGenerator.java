package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.configurations.FGAConfigs;
import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.Population;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.schedulegenerator.models.Shift;
import com.iwm.backend.schedulegenerator.util.GeneticCalculationsUtility;
import com.iwm.backend.trial.EmloyeesReader;

import java.time.LocalDate;
import java.util.*;

/**
 * This class represents the Fuzzy Genetic Algorithm
 * @author Kanishka Withanawasam
 * @version 1.0
 */
public class FuzzyGeneticScheduleGenerator{

    private final List<Employee> employees = EmloyeesReader.readEmployees();
    private FGAConfigs fgaConfigs;
    private double MUTATION_RATE;

    public FuzzyGeneticScheduleGenerator(FGAConfigs fgaConfigs){
        this.fgaConfigs = fgaConfigs;
    }

    /**
     * Determines the optimal schedule
     * @return The optimal schedule
     */
    public WeeklySchedule genSchedule(){

        // 1.Generate Population
        Population population = new Population(
                3,
                4,
                8,
                fgaConfigs.getPopulationSize());

        // 2.Calculate fitness of each schedule in the population
        double bestAccOnPopulation = 0;
        for(WeeklySchedule weeklySchedule : population.getPopulation()){
            weeklySchedule.setFitnessScore(this.getFitnessScore(weeklySchedule));
            if (weeklySchedule.getFitnessScore() > bestAccOnPopulation){
                bestAccOnPopulation=weeklySchedule.getFitnessScore();
            }
        }

        System.out.println("Best Accuracy On Population: " + bestAccOnPopulation);

        // Genetic Algorithm
        double accuracy = 0;
        for (int i = 0; i < fgaConfigs.getNumberOfIterations(); i++) {

            // 3. Adjusting hyper parameters
            adjustFuzzyParameters(population);

            // 4. Selecting parents using tournament selection and applying crossover
            WeeklySchedule parent1 = tournamentSelection(population,20);
            WeeklySchedule parent2 = tournamentSelection(population,20);

            List<WeeklySchedule> offspringWeeklySchedules = crossover(parent1,parent2);

            // 5. Mutating offspring
            for(WeeklySchedule weeklySchedule : offspringWeeklySchedules){
                mutate(weeklySchedule,employees,MUTATION_RATE);
            }

            // 6. Calculating fitness scores.
            for(WeeklySchedule weeklySchedule : offspringWeeklySchedules){
                weeklySchedule.setFitnessScore(this.getFitnessScore(weeklySchedule));
                if (weeklySchedule.getFitnessScore() > accuracy){
                    accuracy=weeklySchedule.getFitnessScore();
                }
            }

            population.setPopulation(selectSurvivors(population.getPopulation(), offspringWeeklySchedules));
        }

        System.out.println("Best Accuracy after Genetic Mutations: " + accuracy);

        return calculateAndConfigure(population.getPopulation());
    }

    /**
     * Calculates the best schedule in the population
     * @param population A list of schedules
     * @return The best schedule
     */
    private WeeklySchedule calculateAndConfigure(List<WeeklySchedule> population) {
        WeeklySchedule ws = Collections.max(population, Comparator.comparingDouble(WeeklySchedule::getFitnessScore));
        //System.out.println(ws.getFitnessScore());
        return ws;
    }


    /**
     * Determines best population after combining the new offspring solutions
     *
     *
     *
     * @param oldPopulation Existing population
     * @param offspring New schedules generated
     * @return The best population
     */
    private List<WeeklySchedule> selectSurvivors(List<WeeklySchedule> oldPopulation, List<WeeklySchedule> offspring) {
        // Sorting both populations based on fitness
        oldPopulation.sort(Comparator.comparingDouble(s -> -s.getFitnessScore()));
        offspring.sort(Comparator.comparingDouble(s -> -s.getFitnessScore()));

        List<WeeklySchedule> newPopulation = new ArrayList<>();

        // Keeping top schedules from the old population
        newPopulation.addAll(oldPopulation.subList(0, oldPopulation.size()-2));
        newPopulation.addAll(offspring);

        if (newPopulation.size() != oldPopulation.size()) {
            throw new RuntimeException("New population size is " + newPopulation.size()
            + " but old population size is " + oldPopulation.size());
        }
        return newPopulation;
    }

    /**
     * Performs mutations by swapping employees between shifts
     * @param weeklySchedule A randomly selected schedule
     * @param employees A list of employees
     * @param mutationRate Mutation rate
     */
    private void mutate(WeeklySchedule weeklySchedule, List<Employee> employees, double mutationRate) {
        Random rand = new Random();

        if (rand.nextDouble() < mutationRate) {

            // Selecting a random shift to mutate
            int index = rand.nextInt(weeklySchedule.getShifts().size());
            Shift shiftToMutate = weeklySchedule.getShifts().get(index);

            LocalDate date = shiftToMutate.getDate();
            Employee newEmployee=null;
            do {
                newEmployee = employees.get(rand.nextInt(employees.size()));
            } while (
                    newEmployee.getId() == shiftToMutate.getEmployee().getId() ||
                            weeklySchedule.getEmpDateMap().get(date).contains(newEmployee)
            );
            // Assigning the new employee
            shiftToMutate.setEmployee(newEmployee);
        }
    }



    private List<WeeklySchedule> crossover(WeeklySchedule parent1, WeeklySchedule parent2){
        Random random = new Random();
        int crossoverIndex = random.nextInt(parent1.getShifts().size());

        List<WeeklySchedule> offspringWeeklySchedules = new ArrayList<>();

        // First part from Parent1, second part from Parent2
        WeeklySchedule offspring1 = new WeeklySchedule();
        WeeklySchedule offspring2 = new WeeklySchedule();

        for (int i = 0; i < crossoverIndex; i++) {
            offspring1.addShift(parent1.getShifts().get(i));
            offspring2.addShift(parent2.getShifts().get(i));
        }
        for (int i = crossoverIndex; i < parent2.getShifts().size(); i++) {
            offspring1.addShift(parent2.getShifts().get(i));
            offspring2.addShift(parent1.getShifts().get(i));
        }

        offspringWeeklySchedules.add(offspring1);
        offspringWeeklySchedules.add(offspring2);
        return offspringWeeklySchedules;
    }


    private WeeklySchedule tournamentSelection(Population population, int tournamentSize) {
        Random rand = new Random();
        List<WeeklySchedule> tournament = new ArrayList<>();

        // Randomly selecting `tournamentSize` schedules
        for (int i = 0; i < tournamentSize; i++) {
            WeeklySchedule randomWeeklySchedule = population.getPopulation().get(rand.nextInt(population.getPopulation().size()));
            tournament.add(randomWeeklySchedule);
        }

        // Return the best schedule from the tournament
        return Collections.max(tournament, Comparator.comparing(WeeklySchedule::getFitnessScore));
    }


    private void adjustFuzzyParameters(Population population) {
        double diversity = calculateDiversity(population);
        double fitnessVariance = calculateFitnessVariance(population);


        if (diversity < 0.05 && fitnessVariance < 0.1) {
            MUTATION_RATE = 0.3;

        } else if (diversity > 0.3 && fitnessVariance > 0.2) {
            MUTATION_RATE = 0.1;
        } else {
            MUTATION_RATE = 0.2;
        }
    }


    private double calculateDiversity(Population population) {
        double avgFitness = population.getPopulation().stream().mapToDouble(WeeklySchedule::getFitnessScore).average().orElse(1);
        return population.getPopulation().stream()
                .mapToDouble(s -> Math.abs(s.getFitnessScore() - avgFitness))
                .sum() / population.getPopulation().size();
    }

    private double calculateFitnessVariance(Population population) {
        double avgFitness = population.getPopulation().stream().mapToDouble(WeeklySchedule::getFitnessScore).average().orElse(1);
        double variance = population.getPopulation().stream()
                .mapToDouble(s -> Math.pow(s.getFitnessScore() - avgFitness, 2))
                .sum() / population.getPopulation().size();
        return Math.sqrt(variance); // Standard deviation
    }


    private double getFitnessScore(WeeklySchedule weeklySchedule){

        return 1 /
                (getTotalCost(weeklySchedule)+getTotalViolation(weeklySchedule)+getTotalDeviation(weeklySchedule)+1);
    }

    private double getTotalCost(WeeklySchedule weeklySchedule){
        double cost = 0;
        for (Shift shift : weeklySchedule.getShifts()) {
            cost+=shift.getEmployee().getCost();
        }
        return cost;
    }

    private double getTotalDeviation(WeeklySchedule weeklySchedule){
        double deviation = 0;
        Map<Employee,Double> totalWeeklyHours = GeneticCalculationsUtility
                .countTotalHours(weeklySchedule.getShifts());
        for (Employee employee : totalWeeklyHours.keySet()) {
            deviation+=Math.abs(totalWeeklyHours.get(employee)-employee.getHoursPreference());
        }
        return deviation;
    }

    private int getTotalViolation(WeeklySchedule weeklySchedule){

        int violation = 0;

        // Adding dates count by employee to the map
        Map<Employee, HashMap<LocalDate, Integer>> dateEmployeeMap = new HashMap<>();
        for (Shift shift : weeklySchedule.getShifts()) {
            Employee employee = shift.getEmployee();

            // Adds the employee and date count to the map if doesn't contain already
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

        // Compute violations
        for(Employee employee : dateEmployeeMap.keySet()){
            for (LocalDate date : dateEmployeeMap.get(employee).keySet()) {
                if(dateEmployeeMap.get(employee).get(date)>1){
                    violation+=5000;
                }
            }
        }


        Map<Employee,Double> totalWeeklyHours = GeneticCalculationsUtility.
                countTotalHours(weeklySchedule.getShifts());
        for (Employee employee : totalWeeklyHours.keySet()) {
            if (totalWeeklyHours.get(employee)>employee.getMaxHoursPerWeek()) {
                violation+=500;
            }
        }

        return violation;
    }

}