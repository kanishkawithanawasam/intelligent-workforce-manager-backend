package com.iwm.backend.schedulegenerator.models;

import com.iwm.backend.schedulegenerator.configurations.FGAConfigs;
import com.iwm.backend.trial.DemandReader;
import com.iwm.backend.trial.EmloyeesReader;

import java.time.LocalDate;
import java.util.*;


/**
 * This class represents a population of schedules required for the genetic algorithm.
 */
public class Population {

    List<WeeklySchedule> population = new ArrayList<>();
    private final int MINIMUM_EMPLOYEES_PER_SHIFT ; // Defines the minimum number of people working at a given time.
    private final int MINIMUM_HOURS_PER_SHIFT;
    private final int MAXIMUM_HOURS_PER_SHIFT ;
    private final int POPULATION_SIZE ;




    /**
     * Generates a population / Pool of random shifts
     * @param MINIMUM_EMPLOYEES_PER_SHIFT Minimum Employees required at any given time
     * @param MINIMUM_HOURS_PER_SHIFT Minimum hours an employee can work in a given shift
     * @param MAXIMUM_HOURS_PER_SHIFT Maximum hours an employee can work in a given shift
     */
    public Population(int MINIMUM_EMPLOYEES_PER_SHIFT, int MINIMUM_HOURS_PER_SHIFT, int MAXIMUM_HOURS_PER_SHIFT) {
        this.MINIMUM_EMPLOYEES_PER_SHIFT = MINIMUM_EMPLOYEES_PER_SHIFT;
        this.MINIMUM_HOURS_PER_SHIFT = MINIMUM_HOURS_PER_SHIFT;
        this.MAXIMUM_HOURS_PER_SHIFT = MAXIMUM_HOURS_PER_SHIFT;
        this.POPULATION_SIZE = FGAConfigs.POPULATION_SIZE;
        this.generatePopulation();
    }

    public List<WeeklySchedule> getPopulation() {
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
    private WeeklySchedule generateRandomSchedule() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        List<Employee> schEmployees = EmloyeesReader.readEmployees(); // Gives a list of employees
        Map<LocalDate,Map<Integer,Integer>> demand = DemandReader.getDemand(); // Represents the hourly demand
        Map<Employee, List<LocalDate>> employeeDateMap = new HashMap<>();

        String[] shiftType = {"opening","midday","evening","closing"};

        // Generate shifts for each day on the demand list
        for(LocalDate date: demand.keySet()) {

            for (String type: shiftType) {
                for (int i = 0; i < MINIMUM_EMPLOYEES_PER_SHIFT; i++) {
                    Shift shift = generateRandomShift(schEmployees,type,date,employeeDateMap);
                    weeklySchedule.addShift(shift);
                }
            }
        }


        return weeklySchedule;

    }


    /**
     * This function generates a random Shift.
     * @param schEmpList  List of Employee objects belong to current schedule.
     * @param type Type of the shift {opening, midday, evening , closing}
     * @param date Date of the shift
     * @return A Shift object with given data.
     */
    private Shift generateRandomShift(List<Employee> schEmpList, String type,
                                      LocalDate date,
                                      Map<Employee, List<LocalDate>> employeeDateMap) {
        Random random = new Random();

        int startTimeInMinutes;
        int endTimeInMinutes;

        // Determine start and end time of the shifts depending on the type
        switch (type) {
            case "opening":
                startTimeInMinutes = 8*60;
                endTimeInMinutes = getEndTime(startTimeInMinutes);
                break;
            case "midday":
                startTimeInMinutes = random.nextInt(10,14)*60-15*random.nextInt(0,3);
                endTimeInMinutes = getEndTime(startTimeInMinutes);
                break;
            case "evening":
                startTimeInMinutes = random.nextInt(14,16)*60-15*random.nextInt(0,3);
                endTimeInMinutes = getEndTime(startTimeInMinutes);
                break;

            case "closing":
                startTimeInMinutes = random.nextInt(16,19)*60 - 15*random.nextInt(0,3);
                endTimeInMinutes = 23*60;
                break;
            default:
                throw new RuntimeException("Something went wrong");
        }



        Collections.shuffle(schEmpList);
        Employee selectedEmployee=null;

        for(Employee emp :  schEmpList){
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

    public void setPopulation(List<WeeklySchedule> population) {
        this.population = population;
    }
}