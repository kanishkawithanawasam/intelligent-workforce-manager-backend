package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.configurations.FGAConfigs;
import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.Population;
import com.iwm.backend.schedulegenerator.models.Schedule;
import com.iwm.backend.schedulegenerator.models.Shift;
import com.iwm.backend.trial.EmloyeesReader;

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
    public Schedule genSchedule(){

        // 1.Generate Population
        Population population = new Population(
                3,
                4,
                8,
                fgaConfigs.getPopulationSize());

        // 2.Calculate fitness of each schedule in the population
        for(Schedule schedule : population.getPopulation()){
            schedule.setFitnessScore(this.getFitnessScore(schedule));
        }

        // Genetic Algorithm
        for (int i = 0; i < fgaConfigs.getNumberOfIterations(); i++) {

            // 3. Adjusting hyper parameters
            adjustFuzzyParameters(population);

            // 4. Selecting parents using tournament selection and applying crossover
            Schedule parent1 = tournamentSelection(population,20);
            Schedule parent2 = tournamentSelection(population,20);
            List<Schedule> offspringSchedules = crossover(parent1,parent2);

            // 5. Mutating offspring
            for(Schedule schedule : offspringSchedules){
                mutate(schedule,employees,MUTATION_RATE);
            }

            // 6. Calculating fitness scores.
            for(Schedule schedule : offspringSchedules){
                schedule.setFitnessScore(this.getFitnessScore(schedule));
            }

            population.setPopulation(selectSurvivors(population.getPopulation(),offspringSchedules));
        }

        return getBestSolution(population.getPopulation());
    }

    /**
     * Calculates the best schedule in the population
     * @param population A list of schedules
     * @return The best schedule
     */
    private Schedule getBestSolution(List<Schedule> population) {
        return Collections.max(population, Comparator.comparingDouble(Schedule::getFitnessScore));
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
    private List<Schedule> selectSurvivors(List<Schedule> oldPopulation, List<Schedule> offspring) {
        // Sorting both populations based on fitness
        oldPopulation.sort(Comparator.comparingDouble(s -> -s.getFitnessScore()));
        offspring.sort(Comparator.comparingDouble(s -> -s.getFitnessScore()));

        List<Schedule> newPopulation = new ArrayList<>();

        // Keeping top schedules from the old population
        newPopulation.addAll(oldPopulation.subList(0, oldPopulation.size()));
        newPopulation.addAll(offspring);

        if (newPopulation.size() != oldPopulation.size()) {
            throw new RuntimeException("New population size is " + newPopulation.size());
        }
        return newPopulation;
    }

    /**
     * Performs mutations by swapping employees between shifts
     * @param schedule A randomly selected schedule
     * @param employees A list of employees
     * @param mutationRate Mutation rate
     */
    private void mutate(Schedule schedule, List<Employee> employees, double mutationRate) {
        Random rand = new Random();

        if (rand.nextDouble() < mutationRate) {

            // Selecting a random shift to mutate
            int index = rand.nextInt(schedule.getShifts().size());
            Shift shiftToMutate = schedule.getShifts().get(index);

            // Selecting a new employee for the shift
            Employee newEmployee;
            do {
                newEmployee = employees.get(rand.nextInt(employees.size()));
            } while (newEmployee.getId()==(shiftToMutate.getEmployee().getId())); // Avoiding assignments on same emp

            // Assigning the new employe
            shiftToMutate.setEmployee(newEmployee);
        }
    }



    private List<Schedule> crossover(Schedule parent1, Schedule parent2){
        Random random = new Random();
        int crossoverIndex = random.nextInt(parent1.getShifts().size());

        List<Schedule> offspringSchedules = new ArrayList<>();

        // First part from Parent1, second part from Parent2
        Schedule offspring1 = new Schedule();
        Schedule offspring2 = new Schedule();
        for (int i = 0; i < crossoverIndex; i++) {
            offspring1.addShift(parent1.getShifts().get(i));
            offspring2.addShift(parent2.getShifts().get(i));
        }
        for (int i = crossoverIndex; i < parent2.getShifts().size(); i++) {
            offspring1.addShift(parent2.getShifts().get(i));
            offspring2.addShift(parent1.getShifts().get(i));
        }

        return offspringSchedules;
    }


    private Schedule tournamentSelection(Population population, int tournamentSize) {
        Random rand = new Random();
        List<Schedule> tournament = new ArrayList<>();

        // Randomly selecting `tournamentSize` schedules
        for (int i = 0; i < tournamentSize; i++) {
            Schedule randomSchedule = population.getPopulation().get(rand.nextInt(population.getPopulation().size()));
            tournament.add(randomSchedule);
        }

        // Return the best schedule from the tournament
        return Collections.max(tournament, Comparator.comparing(Schedule::getFitnessScore));
    }


    private void adjustFuzzyParameters(Population population) {
        double diversity = calculateDiversity(population);
        double fitnessVariance = calculateFitnessVariance(population);


        if (diversity < 0.05 && fitnessVariance < 0.1) {
            MUTATION_RATE = 0.2;

        } else if (diversity > 0.3 && fitnessVariance > 0.2) {
            MUTATION_RATE = 0.05;
        } else {
            MUTATION_RATE = 0.1;
        }
    }


    private double calculateDiversity(Population population) {
        double avgFitness = population.getPopulation().stream().mapToDouble(Schedule::getFitnessScore).average().orElse(1);
        return population.getPopulation().stream()
                .mapToDouble(s -> Math.abs(s.getFitnessScore() - avgFitness))
                .sum() / population.getPopulation().size();
    }

    private double calculateFitnessVariance(Population population) {
        double avgFitness = population.getPopulation().stream().mapToDouble(Schedule::getFitnessScore).average().orElse(1);
        double variance = population.getPopulation().stream()
                .mapToDouble(s -> Math.pow(s.getFitnessScore() - avgFitness, 2))
                .sum() / population.getPopulation().size();
        return Math.sqrt(variance); // Standard deviation
    }


    private double getFitnessScore(Schedule schedule){
        return 1 /
                (getTotalCost(schedule)+getTotalViolation(schedule)+getTotalDeviation(schedule)+1);
    }

    private double getTotalCost(Schedule schedule){
        double cost = 0;
        for (Shift shift : schedule.getShifts()) {
            cost+=shift.getEmployee().getCost();
        }
        return cost;
    }

    private double getTotalDeviation(Schedule schedule){
        double deviation = 0;
        Map<Employee,Double> totalWeeklyHours = getTotalWeeklyHours(schedule);
        for (Employee employee : totalWeeklyHours.keySet()) {
            Math.abs(totalWeeklyHours.get(employee)-employee.getHoursPreference());
        }
        return deviation;
    }

    private int getTotalViolation(Schedule schedule){
        int violation = 0;
        Map<Employee,Map<String,Integer>>  dateEmployeeMap= new HashMap<>();
        Map<Employee,Double> totalWeeklyHours = getTotalWeeklyHours(schedule);


        // Adding dates count by employee to the map
        for (Shift shift : schedule.getShifts()) {
            Employee employee = shift.getEmployee();

            // Adds the employee and date count to the map if doesn't contain already
            if(!dateEmployeeMap.containsKey(employee)){
                Map<String,Integer> employeeWeeklyHours=new HashMap<>();
                employeeWeeklyHours.put(shift.getDate(),1);
                dateEmployeeMap.put(employee,employeeWeeklyHours);
            }else{
                dateEmployeeMap.get(employee).compute(shift.getDate(), (k, count) -> count + 1);
            }
        }

        // Compute violations
        for(Employee employee : dateEmployeeMap.keySet()){
            for (String date : dateEmployeeMap.get(employee).keySet()) {
                if(dateEmployeeMap.get(employee).get(date)>1){
                    violation+=1000;
                }
            }
        }


        for (Employee employee : totalWeeklyHours.keySet()) {
            if (totalWeeklyHours.get(employee)>employee.getMaxHoursPerWeek()) {
                violation+=5000;
            }
        }


        return violation;
    }


    /**
     * Calculates total hours an employee worked
     * @param schedule The schedule of the week
     * @return Mapping of the employee and number of hours they worked during the week.
     */
    private Map<Employee,Double> getTotalWeeklyHours(Schedule schedule){
        Map<Employee,Double> totalWeeklyHours= new HashMap<>();
        for (Shift shift : schedule.getShifts()) {
            if(totalWeeklyHours.containsKey(shift.getEmployee())) {
                totalWeeklyHours.put(shift.getEmployee(),
                        totalWeeklyHours.get(shift.getEmployee())+shift.getShiftDuration());
            }else{
                totalWeeklyHours.put(shift.getEmployee(),shift.getShiftDuration());
            }
        }
        return totalWeeklyHours;
    }

}