package com.iwm.backend.schedulegenerator.models;

import com.iwm.backend.trial.DemandReader;
import com.iwm.backend.trial.EmloyeesReader;

import java.util.*;


/**
 * This class represents a population of schedules required for the genetic algorithm.
 */
public class Population {

    List<Schedule> population = new ArrayList<>();
    private final int MINIMUM_EMPLOYEES_PER_SHIFT ; // Defines the minimum number of people working at a given time.
    private final int MINIMUM_HOURS_PER_SHIFT;
    private final int MAXIMUM_HOURS_PER_SHIFT ;
    private final int POPULATION_SIZE ;
    private final Map<String,Map<Integer,Integer>> demand;
    private final List<Employee> employees;
    Map<Employee,Double> totalHoursInWeek;



    /**
     * Generates a population / Pool of random shifts
     * @param MINIMUM_EMPLOYEES_PER_SHIFT Minimum Employees required at any given time
     * @param MINIMUM_HOURS_PER_SHIFT Minimum hours an employee can work in a given shift
     * @param MAXIMUM_HOURS_PER_SHIFT Maximum hours an employee can work in a given shift
     * @param POPULATION_SIZE Population / Shift pool size
     */
    public Population(int MINIMUM_EMPLOYEES_PER_SHIFT, int MINIMUM_HOURS_PER_SHIFT, int MAXIMUM_HOURS_PER_SHIFT,
                      int POPULATION_SIZE) {
        this.MINIMUM_EMPLOYEES_PER_SHIFT = MINIMUM_EMPLOYEES_PER_SHIFT;
        this.MINIMUM_HOURS_PER_SHIFT = MINIMUM_HOURS_PER_SHIFT;
        this.MAXIMUM_HOURS_PER_SHIFT = MAXIMUM_HOURS_PER_SHIFT;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.demand = DemandReader.getDemand();
        this.employees = EmloyeesReader.readEmployees();
        this.generatePopulation();
    }

    public List<Schedule> getPopulation() {
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
            population.add(generateRandomScheule());
        }
    }

    /**
     * This function generates a random schedule.
     * @return A schedule generated randomly.
     */
    private Schedule generateRandomScheule() {
        Schedule schedule = new Schedule();
        List<Employee> schEmployees = new ArrayList<>(employees);
        totalHoursInWeek = new HashMap<>();


        String[] shiftType = {"opening","midday","evening","closing"};

        // Generate shifts for each day on the demand list
        // TODO : Adjust the code tho consider demand when generating initial schedule.
        for(String date: demand.keySet()) {
            for (String type: shiftType) {
                for (int i = 0; i < MINIMUM_EMPLOYEES_PER_SHIFT; i++) {
                    Shift shift = generateRandomShift(schEmployees,type,date);

                    // Add shifts to the current schedule.
                    schedule.addShift(shift);
                }
            }
        }

        return schedule;

    }


    /**
     * This function generates a random Shift.
     * @param empList  List of employees
     * @param type Type of the shift {opening, midday, evening , closing}
     * @param date Date of the shift
     * @return A Shift object with given data.
     */
    private Shift generateRandomShift(List<Employee> empList,String type, String date) {
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
                startTimeInMinutes = random.nextInt(10,14)*60;
                endTimeInMinutes = getEndTime(startTimeInMinutes);
                break;
            case "evening":
                startTimeInMinutes = random.nextInt(14,16)*60;
                endTimeInMinutes = getEndTime(startTimeInMinutes);
                break;

            case "closing":
                startTimeInMinutes = random.nextInt(16,19)*60;
                endTimeInMinutes = 23*60;
                break;
            default:
                throw new RuntimeException("Something went wrong");
        }

        // determine an employee

        Collections.shuffle(empList);
        Employee employee=null;

        // Checks if there is a suitable employee.
        while (true){
            for (Employee emp: empList) {
                double totalShiftHours = (startTimeInMinutes-endTimeInMinutes)/60.0;


                if(emp.getMaxWeeklyHours() > totalHoursInWeek.getOrDefault(employee,0.0)+totalShiftHours) {
                    employee = emp;
                    totalHoursInWeek.put(employee,totalHoursInWeek.getOrDefault(employee,0.0)+totalShiftHours);
                    break;
                }
            }

            if(employee==null){
                throw new RuntimeException("No employee found");
            }

            break;
        }

        return new Shift(date,startTimeInMinutes,endTimeInMinutes, employee,type);
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
                MAXIMUM_HOURS_PER_SHIFT)*60;
        return  startTime + endTimeInMinutes;
    }

    public void setPopulation(List<Schedule> population) {
        this.population = population;
    }
}