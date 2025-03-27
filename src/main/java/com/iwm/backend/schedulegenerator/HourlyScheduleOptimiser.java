package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.configurations.HSOConfigs;
import com.iwm.backend.schedulegenerator.models.*;
import com.iwm.backend.schedulegenerator.util.CalculationsUtility;

import java.util.*;

public class HourlyScheduleOptimiser{

    private final HSOConfigs configs;                           // HSOConfigurations.
    private final WeeklySchedule weeklySchedule;                // Schedule of the week.
    private final HourlyDemand hourlyDemand;                    // Hourly demand for a particular period.
    private final Map<Employee,Double> totalHoursInWeek;        // Total Hours in the week.
    private Map<Employee, Double> hoursInOrgRTSchedule;   // Hours of employees in org realtime schedule.

    public HourlyScheduleOptimiser(HSOConfigs configs, WeeklySchedule schedule, HourlyDemand hourlyDemand) {
        this.configs = configs;
        this.weeklySchedule = schedule;
        this.hourlyDemand = hourlyDemand;
        this.totalHoursInWeek = CalculationsUtility.countTotalHours(weeklySchedule.getShifts());
    }

    public RealTimeSchedule getOptimisedRealTimeSchedule() {


        // =============== Simulated Annealing for Time Adjustments =============

        // Variable initialisation
        RealTimeSchedule currentSolution = new RealTimeSchedule();  // Current Solution
        RealTimeSchedule bestSolution = null;                       // Best Solution
        double T = configs.getINITIAL_TEMPERATURE();             // Initial Temperature
        final double minT = configs.getMINIMUM_TEMPERATURE();    // Minimum Temperature
        double coolingRate = configs.getCOOLING_RATE();          // Cooling rate
        RealTimeSchedule newSolution;                               // Candidate solution in each iteration
        double deltaFitness;                                        // Fitness difference or delta fitness.
        Random random = new Random();

        // Initialise the current solution by selecting shifts that starts or ends within demand period.
        for (Shift shift :weeklySchedule.getShifts()) {
            if (shift.getDate().equals(hourlyDemand.getDate()) &&
                    (shiftStartOrEndInDmdPeriod(shift) == -1 || shiftStartOrEndInDmdPeriod(shift) == 1)) {
                currentSolution.getShifts().add(shift);
            }
        }

        // Calculate each employee hours in selected schedule before any mutations.
        hoursInOrgRTSchedule = CalculationsUtility.countTotalHours(currentSolution.getShifts());


        // Simulated annealing loop.
        while (T>minT) {

            // Generate the neighbouring solution by mutating the current one.
            newSolution = mutate(currentSolution);

            // Calculate the fitness difference
            deltaFitness = currentSolution.getFitness()-newSolution.getFitness();

            // Accept the new solution if its better
            if (deltaFitness>0) {
                currentSolution = newSolution;
            }
            // Otherwise, accept new solution using probability based on temperature.
            else if (random.nextDouble(0,1) < Math.exp(deltaFitness/T)) {
                currentSolution = newSolution;
            }


            // Update the best solution
            if(Objects.isNull(bestSolution)){
                currentSolution.setFitness(calculateFitnessScore(currentSolution));
                bestSolution = currentSolution;
            }else{
                if (calculateFitnessScore(currentSolution)>bestSolution.getFitness()) {
                    currentSolution.setFitness(calculateFitnessScore(currentSolution));
                    bestSolution = currentSolution;
                }
            }

            // Update the temperature
            T=T*coolingRate;
        }


        return bestSolution;

    }


    private double calculateFitnessScore(RealTimeSchedule realTimeSchedule) {

        double totalRewards = 1;
        double totalPenalties = getTotalViolationsPenalty(realTimeSchedule)+
                getTotalDeviationsPenalty(realTimeSchedule);

        return totalRewards/totalPenalties;
    }

    /**
     * Calculates the total deviation penalty based on how far each employee's scheduled hours
     * deviate from their preferred working hours.
     *
     * <p>This function measures how well the generated schedule aligns with individual employee
     * preferences, specifically in terms of total hours worked within the week. The penalty is
     * computed as the absolute difference between actual and preferred hours for each employee,
     * and then summed across all employees.</p>
     *
     * <p>This metric helps the optimization algorithm to favor schedules that not only meet demand
     * but also respect employee preferences, improving satisfaction and fairness.</p>
     *
     * @param realTimeSchedule the schedule to be evaluated
     * @return the total deviation penalty across all employees
     */
    private double getTotalDeviationsPenalty(RealTimeSchedule realTimeSchedule) {
        Map<Employee,Double> weeklyWorkedHours = new HashMap<>();

        // Aggregate shift durations for each employee
        for (Shift shift : realTimeSchedule.getShifts()) {
            weeklyWorkedHours.put(
                    shift.getEmployee(),
                    weeklyWorkedHours.getOrDefault(shift.getEmployee(), 0.0)+
                            shift.getShiftDuration());
        }

        double totalDeviationsPenalty = 0;

        // Calculate the absolute deviation from each employee's preferred hours
        for(Employee employee: weeklyWorkedHours.keySet()) {
            totalDeviationsPenalty +=
                     Math.abs(weeklyWorkedHours.get(employee)-employee.getHoursPreference());
        }

        return totalDeviationsPenalty;
    }


    /**
     * Calculates the total violation penalty incurred when updating a real-time schedule,
     * based on weekly hour constraints for each employee.
     *
     * <p>This method compares the adjusted weekly hours (including the proposed changes
     * from the new real-time schedule) with each employee's maximum allowed weekly hours.
     * If the proposed change causes an employee to exceed their weekly hour limit,
     * a fixed penalty of 10 units is applied per violation.</p>
     *
     * <p>The comparison considers:</p>
     * <ul>
     *   <li>{@code totalHoursInWeek} – the employee's current total weekly hours before the update</li>
     *   <li>{@code hoursInOrgRealTimeSchedule} – the original hours in the real-time schedule being replaced</li>
     *   <li>{@code newRealTimeSchedule} – the new proposed schedule with updated shift assignments</li>
     * </ul>
     *
     * @param newRealTimeSchedule the new real-time schedule to be evaluated
     * @return the total penalty incurred for constraint violations
     */
    private double getTotalViolationsPenalty(RealTimeSchedule newRealTimeSchedule) {

        // Total hours of each employee in realtime schedule.
        Map<Employee,Double> newRTScheduleHoursMap = new HashMap<>();

        double violationPenalty = 0.00;

        // Update the record of total hours of each employee in realtime schedule.
        for (Shift shift : newRealTimeSchedule.getShifts()) {
            newRTScheduleHoursMap.put(
                    shift.getEmployee(),
                    newRTScheduleHoursMap.getOrDefault(shift.getEmployee(), 0.0)+
                            shift.getShiftDuration()
            );
        }

        // Calculate weekly hours violations penalty for each employee in RT schedule
        for (Employee employee : newRTScheduleHoursMap.keySet()) {
            double orgWeeklyWorkedHours = totalHoursInWeek.get(employee);
            double newRTScheduleDelta = hoursInOrgRTSchedule.get(employee)-newRTScheduleHoursMap.get(employee);
            double projectedWeeklyHours = newRTScheduleDelta+orgWeeklyWorkedHours;

            if (projectedWeeklyHours > employee.getMaxHoursPerWeek()) {
                violationPenalty+=10;
            }
        }

        return violationPenalty;
    }


    /**
     * Determines the temporal relationship between a given shift and the real-time demand period.
     *
     * <p>This method evaluates whether a shift either ends within or starts within the time range
     * specified by the {@code hourlyDemand} object. It returns an integer code that indicates
     * the shift's position relative to the demand period.</p>
     *
     * <ul>
     *     <li>Returns {@code -1} if the shift ends within the demand period.</li>
     *     <li>Returns {@code 1} if the shift starts within the demand period.</li>
     *     <li>Returns {@code 0} if the shift neither starts nor ends within the demand period.</li>
     * </ul>
     *
     * <p>Use this method to identify shifts that are partially overlapping with a specified demand
     * window and thus may be eligible for optimization in real-time scheduling.</p>
     *
     * @param shift the shift to be evaluated
     * @return an integer code indicating the shift's overlap type:
     *         {@code -1} for end-inside, {@code 1} for start-inside, {@code 0} for no overlap
     */
    private int shiftStartOrEndInDmdPeriod(Shift shift) {

        // Case 1: The shift ends within the demand period
        if(hourlyDemand.getStartTimeInMinutes()< shift.getEndTimeInMinutes() &&
                shift.getEndTimeInMinutes() < hourlyDemand.getEndTimeInMinutes()){
            return -1;
        }

        // Case 2: The shift starts within the demand period
        if(hourlyDemand.getStartTimeInMinutes() < shift.getStartTimeInMinutes() &&
                shift.getStartTimeInMinutes() < hourlyDemand.getEndTimeInMinutes()){
            return 1;
        }

        // Case 3: The shift neither starts nor ends within the demand window
        return 0;
    }


    /**
     * Performs a mutation operation on the given real-time schedule to generate a new candidate schedule.
     *
     * <p>This method randomly adjusts the start or end times of shifts that either start or end within
     * the specified hourly demand period. The goal of this mutation is to explore neighboring solutions
     * by slightly modifying the timing of shifts, which is useful for metaheuristic algorithms such as
     * Simulated Annealing or Genetic Algorithms.</p>
     *
     * <p>The time adjustment is done in 15-minute intervals, and constrained within the bounds of
     * the demand window. This ensures the resulting schedule still satisfies basic temporal validity.</p>
     *
     * @param realTimeSchedule the original schedule to mutate
     * @return a new {@code RealTimeSchedule} with slight random variations applied
     */
    private RealTimeSchedule mutate(RealTimeSchedule realTimeSchedule) {


        Random random = new Random();

        // Define the number of 15-minute adjustment units available within the demand window
        int maximumAdjustment = (hourlyDemand.getEndTimeInMinutes() - hourlyDemand.getStartTimeInMinutes()) / 15;

        // Create a new temporary schedule to store mutated shifts
        RealTimeSchedule tempSchedule = new RealTimeSchedule();

        // Iterate through each shift in the original schedule
        for (Shift shift : realTimeSchedule.getShifts()) {

            // Clone the shift to avoid modifying the original directly
            Shift temp = shift.clone();

            // Case 1: Shift ends within the demand window — mutate its end time
            if (shiftStartOrEndInDmdPeriod(temp) == -1) {
                temp.setEndTimeInMinutes(
                        hourlyDemand.getStartTimeInMinutes() + random.nextInt(0, maximumAdjustment) * 15);
            }
            // Case 2: Shift starts within the demand window — mutate its start time
            else if (shiftStartOrEndInDmdPeriod(shift) == 1) {
                temp.setStartTimeInMinutes(
                        hourlyDemand.getEndTimeInMinutes() - random.nextInt(0, maximumAdjustment) * 15);
            }

            // Add the mutated shift to the new schedule
            tempSchedule.getShifts().add(temp);
        }
        return tempSchedule;
    }
}
