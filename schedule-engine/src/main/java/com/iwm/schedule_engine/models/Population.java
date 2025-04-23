package com.iwm.schedule_engine.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwm.schedule_engine.configurations.BusinessConfigs;
import com.iwm.schedule_engine.configurations.FGAConfigs;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;


/**
 * This class represents a population of schedules required for the genetic algorithm.
 */
public class Population {

    List<WeeklyScheduleChromosome> population = new ArrayList<>();
    private final int MINIMUM_EMPLOYEES_PER_SHIFT ; // Defines the minimum number of people working at a given time.
    private final int MINIMUM_HOURS_PER_SHIFT;
    private final int MAXIMUM_HOURS_PER_SHIFT ;
    private final int POPULATION_SIZE ;
    private List<Employee> employees;
    Map<LocalDate,Map<Integer,Integer>> demand;


    public Population(List<Employee> employees,Map<LocalDate,Map<Integer,Integer>> demand) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/BusinessConfigs.json");
        BusinessConfigs businessConfigs = objectMapper.readValue( inputStream, BusinessConfigs.class);

        this.MINIMUM_EMPLOYEES_PER_SHIFT = businessConfigs.minimum_employees_per_shift;
        this.MINIMUM_HOURS_PER_SHIFT = businessConfigs.employee_hours_limits.minimum_hours;
        this.MAXIMUM_HOURS_PER_SHIFT = businessConfigs.employee_hours_limits.maximum_hours;
        this.POPULATION_SIZE = FGAConfigs.POPULATION_SIZE;

        this.employees = employees;
        this.demand = demand;

        this.generatePopulation();
    }

    public List<WeeklyScheduleChromosome> getPopulation() {
        return population;
    }

    /**
     * This function initialises a pool of randomly generated shifts.
     */
    private void generatePopulation() {

        // Until population size if POPULATION_SIZE
        //      1. Generate a random Schedule for the week
        //      2. Add it to the population
        for(int i=1;i<=POPULATION_SIZE;i++){
            population.add(generateRandomSchedule());
        }
    }

    /**
     * This function generates a random schedule.
     * @return A schedule generated randomly.
     */
    private WeeklyScheduleChromosome generateRandomSchedule() {

        WeeklyScheduleChromosome weeklyScheduleChromosome = new WeeklyScheduleChromosome();
        Map<Employee, List<LocalDate>> employeeDateMap = new HashMap<>();

        String[] shiftType = {"opening","midday","evening","closing"};

        // Generate shifts for each day on the demand list
        for(LocalDate date: demand.keySet()) {

            for (String type: shiftType) {
                for (int i = 0; i < MINIMUM_EMPLOYEES_PER_SHIFT; i++) {
                    Shift shift = generateRandomShift(type,date,employeeDateMap);
                    weeklyScheduleChromosome.addShift(shift);
                }
            }
        }


        return weeklyScheduleChromosome;

    }


    /**
     * This function generates a random Shift.
     * @param type Type of the shift {opening, midday, evening , closing}
     * @param date Date of the shift
     * @return A Shift object with given data.
     */
    private Shift generateRandomShift(String type,
                                      LocalDate date,
                                      Map<Employee, List<LocalDate>> employeeDateMap) {
        Random random = new Random();

        // Determine start and end time of the shifts depending on the type
        int startTimeInMinutes;
        int endTimeInMinutes = switch (type) {
            case "opening" -> {
                startTimeInMinutes = 8 * 60;
                yield getEndTime(startTimeInMinutes);
            }
            case "midday" -> {
                startTimeInMinutes = random.nextInt(10, 14) * 60 - 15 * random.nextInt(0, 3);
                yield getEndTime(startTimeInMinutes);
            }
            case "evening" -> {
                startTimeInMinutes = random.nextInt(14, 16) * 60 - 15 * random.nextInt(0, 3);
                yield getEndTime(startTimeInMinutes);
            }
            case "closing" -> {
                startTimeInMinutes = random.nextInt(16, 19) * 60 - 15 * random.nextInt(0, 3);
                yield 23 * 60;
            }
            default -> throw new RuntimeException("Something went wrong");
        };


        Collections.shuffle(employees);
        Employee selectedEmployee=null;

        for(Employee emp :  employees){
            if (!employeeDateMap.getOrDefault(emp,new ArrayList<>()).contains(date)) {
                selectedEmployee = emp;
                List<LocalDate> employeeDateList = employeeDateMap.getOrDefault(emp,new ArrayList<>());
                employeeDateList.add(date);
                employeeDateMap.put(emp,employeeDateMap.getOrDefault(emp,employeeDateList));
                break;
            }
        }

        if (selectedEmployee == null){
            throw new RuntimeException("The selected employee is null");
        }

        return new Shift(date,startTimeInMinutes,endTimeInMinutes,selectedEmployee);

    }


    /**
     * Calculates an end time in minutes for a given start time in minutes
     * @param startTime Start time in minutes
     * @return End time in minutes.
     */
    private int getEndTime(int startTime) {
        Random random = new Random();

        // Get a random end time considering maximum and minimum working hours.
        int endTimeInMinutes = random.nextInt(MINIMUM_HOURS_PER_SHIFT,
                MAXIMUM_HOURS_PER_SHIFT)*60 + 15*random.nextInt(0,3);
        return  startTime + endTimeInMinutes;
    }

    public void setPopulation(List<WeeklyScheduleChromosome> population) {
        this.population = population;
    }
}