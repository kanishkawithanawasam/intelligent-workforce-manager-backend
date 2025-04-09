package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.ShiftGO;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.schedulegenerator.configurations.HSOConfigs;
import com.iwm.backend.schedulegenerator.exceptions.DemandNotFoundException;
import com.iwm.backend.schedulegenerator.exceptions.InvalidDemandMapException;
import com.iwm.backend.schedulegenerator.models.*;
import com.iwm.backend.schedulegenerator.util.CalculationsUtility;

import java.util.*;


/**
 * Optimizes employee shift schedules in real-time to satisfy hourly demand
 * while minimizing constraint violations and deviations from preferences.
 *
 * <p>The {@code HourlyScheduleOptimiser} uses a meta heuristic optimization technique
 * (such as Simulated Annealing) to adjust shift start/end times for a specific demand
 * window. It ensures that weekly working hour limits are respected and that employee
 * preferences are taken into account.</p>
 *
 * <p>It works with a given weekly schedule, demand instance, and configuration
 * parameters provided via {@link HSOConfigs}.</p>
 */
public class HourlyScheduleOptimiser{

    private final WeeklySchedule weeklySchedule;                // Schedule of the week.
    private final HourlyDemand hourlyDemand;                    // Hourly demand for a particular period.
    private final Map<Employee,Double> totalHoursInWeek;        // Total Hours in the week.
    private Map<Employee, Double> orgRTScheduleHours;           // Hours of employees in org realtime schedule.


    /**
     * Constructs a new {@code HourlyScheduleOptimiser} instance.
     *
     * @param schedule       the complete weekly schedule containing all shifts
     * @param hourlyDemand   the specific hourly demand window to optimize for
     */
    public HourlyScheduleOptimiser( WeeklySchedule schedule, HourlyDemand hourlyDemand) {
        this.weeklySchedule = schedule;
        this.hourlyDemand = hourlyDemand;

        // Precompute total hours each employee has already worked this week.
        this.totalHoursInWeek = CalculationsUtility.countTotalHours(weeklySchedule.getShifts());
    }

    /**
     * Optimizes a real-time shift schedule using the Simulated Annealing algorithm.
     *
     * <p>This method initializes a starting schedule with shifts that begin or end during the current
     * hourly demand period. It then iteratively mutates the schedule to explore neighboring solutions
     * and attempts to find a schedule with the highest possible fitness score while gradually reducing
     * the temperature.</p>
     *
     * <p>The fitness score balances constraint violations (like exceeding max weekly hours) and
     * preference deviations (like mismatch with preferred hours). Simulated Annealing is used to
     * probabilistically accept worse solutions to escape local optima during early iterations.</p>
     *
     * @return the best real-time schedule found through the optimization process
     */
    public RealTimeSchedule getOptimisedRealTimeSchedule() {


        // =============== Simulated Annealing for Time Adjustments =============

        // Variable initialisation
        RealTimeSchedule currentSolution = new RealTimeSchedule();  // Current Solution
        RealTimeSchedule bestSolution = null;                       // Best Solution
        double T = HSOConfigs.INITIAL_TEMPERATURE;                  // Initial Temperature
        final double minT = HSOConfigs.MINIMUM_TEMPERATURE;         // Minimum Temperature
        double coolingRate = HSOConfigs.COOLING_RATE;               // Cooling rate
        RealTimeSchedule newSolution;                               // Candidate solution in each iteration
        double deltaFitness;                                        // Fitness difference or delta fitness.
        Random random = new Random();

        // Initialise the current solution by selecting shifts that starts or ends within demand period.
        for (ShiftGO shift :weeklySchedule.getShifts()) {
            if (shift.getDate().equals(hourlyDemand.getDate()) &&
                    (shiftStartOrEndInDmdPeriod(shift) == -1 || shiftStartOrEndInDmdPeriod(shift) == 1)) {
                currentSolution.getShifts().add(shift);
            }
        }

        // Calculate each employee hours in selected schedule before any mutations.
        orgRTScheduleHours = CalculationsUtility.countTotalHours(currentSolution.getShifts());


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


    /**
     * Calculates the fitness score of a given real-time schedule.
     *
     * <p>The fitness score is defined as the ratio of total rewards to total penalties.
     * In this implementation, the reward is fixed at 1, and penalties are the sum of:
     * <ul>
     *   <li>Violation penalties – for exceeding weekly working hour constraints</li>
     *   <li>Deviation penalties – for deviating from preferred working hours</li>
     * </ul>
     *
     * <p>A lower penalty results in a higher fitness score. If the total penalty is zero,
     * the method returns {@code Double.MAX_VALUE} to indicate a perfect schedule.</p>
     *
     * @param realTimeSchedule the schedule for which the fitness score is to be calculated
     * @return the fitness score as a double; higher values indicate better fitness
     */
    private double calculateFitnessScore(RealTimeSchedule realTimeSchedule) {

        double totalRewards = demandSatisfactionScore(realTimeSchedule);
        double totalPenalties = getTotalViolationsPenalty(realTimeSchedule)+
                getTotalDeviationsPenalty(realTimeSchedule);

        return totalRewards/(totalPenalties+1);
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

        // Map of employee and hours in updated RealTimeSchedule.
        Map<Employee,Double> newRTScheduleHoursMap = CalculationsUtility.countTotalHours(realTimeSchedule.getShifts());

        double totalDeviationsPenalty = 0;

        // Calculate the absolute deviation from each employee's preferred hours
        for(Employee employee: newRTScheduleHoursMap.keySet()) {

            double realTimeSchedulesDelta = newRTScheduleHoursMap.get(employee)- orgRTScheduleHours.get(employee);

            totalDeviationsPenalty +=
                     Math.abs(realTimeSchedulesDelta+totalHoursInWeek.get(employee));
        }

        return totalDeviationsPenalty;
    }


    private double demandSatisfactionScore(RealTimeSchedule realTimeSchedule) {

        TreeMap<Integer,Integer> _15MinWinEmpMap = new TreeMap<>();
        TreeMap<Integer,Integer> _15minWinDmdMap = new TreeMap<>();

        // Verify hourly demand is consistent
        List<Integer> demandKeyList= new ArrayList<>(hourlyDemand.getHourlyDemandMap().keySet());
        if (demandKeyList.size()<2){throw new DemandNotFoundException();}
        int demandKey = demandKeyList.get(0);
        for (int i = 1; i < demandKeyList.size(); i++) {
            if(demandKey!=demandKeyList.get(i)-1) {
                throw new InvalidDemandMapException();
            }
        }


        //  Initialise demand map for each 15 minutes.
        for (int i = hourlyDemand.getStartTimeInMinutes(); i < hourlyDemand.getEndTimeInMinutes(); i+=15) {
            _15minWinDmdMap.put(i, hourlyDemand.getHourlyDemandMap().get(i/60));
        }


        // Initialise the employees count in each 15 minutes
        for (int i = hourlyDemand.getStartTimeInMinutes(); i < hourlyDemand.getEndTimeInMinutes(); i+=15) {
            _15MinWinEmpMap.put(i,0);
        }

        // Update the employees count
        for (ShiftGO shift : realTimeSchedule.getShifts()) {
            int counter;
            switch (shiftStartOrEndInDmdPeriod(shift)){

                case -1:
                    counter = hourlyDemand.getStartTimeInMinutes();
                    while (counter<shift.getEndTimeInMinutes()){
                        if (_15MinWinEmpMap.containsKey(counter)) {
                            _15MinWinEmpMap.put(counter,_15MinWinEmpMap.get(counter)+1);
                        }
                        counter+=15;
                    }
                    break;

                case 1:
                    counter = shift.getStartTimeInMinutes();
                    while (counter<hourlyDemand.getEndTimeInMinutes()){
                        if (_15MinWinEmpMap.containsKey(counter)) {
                            _15MinWinEmpMap.put(counter,_15MinWinEmpMap.get(counter)+1);
                        }
                        counter+=15;
                    }
                    break;

                default:
                    break;
            }
        }

        int demandSatisfactionScore = 0;

        for(int timeSlot :  _15minWinDmdMap.keySet()) {
            demandSatisfactionScore += _15minWinDmdMap.get(timeSlot) -
                    (_15minWinDmdMap.get(timeSlot)-_15MinWinEmpMap.get(timeSlot));
        }

        return demandSatisfactionScore;
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

        double totalViolationPenalty = 0.00;

        // Calculate daily hours violations penalty.
        for (ShiftGO shift : newRealTimeSchedule.getShifts()) {
            if(shift.getShiftLengthInMinutes()/60.0 < HSOConfigs.DAILY_MIN_HOURS ||
                shift.getShiftLengthInMinutes()/60.0 > HSOConfigs.DAILY_MAX_HOURS) {
                totalViolationPenalty += HSOConfigs.VIOLATIONS_PENALTY_DAYILY_HOURS;
            }
        }


        // Total hours of each employee in realtime(RT) schedule.
        Map<Employee,Double> newRTScheduleHoursMap =
                CalculationsUtility.countTotalHours(newRealTimeSchedule.getShifts());

        // Calculate weekly hours violations penalty for each employee in RT schedule
        for (Employee employee : newRTScheduleHoursMap.keySet()) {
            double orgWeeklyWorkedHours = totalHoursInWeek.get(employee);
            double newRTScheduleDelta = orgRTScheduleHours.get(employee)-newRTScheduleHoursMap.get(employee);
            double projectedWeeklyHours = newRTScheduleDelta+orgWeeklyWorkedHours;

            if (projectedWeeklyHours > employee.getMaxHoursPerWeek()) {
                totalViolationPenalty+=HSOConfigs.VIOLATIONS_PENALTY_WEEKLY_HOURS;
            }
        }

        return totalViolationPenalty;
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
    private int shiftStartOrEndInDmdPeriod(ShiftGO shift) {

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
     * by slightly modifying the timing of shifts, which is useful for meta heuristic algorithms such as
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
        for (ShiftGO shift : realTimeSchedule.getShifts()) {

            // Clone the shift to avoid modifying the original directly
            ShiftGO temp = shift.clone();

            // Case 1: ShiftGO ends within the demand window — mutate its end time
            if (shiftStartOrEndInDmdPeriod(temp) == -1) {
                temp.setEndTimeInMinutes(
                        hourlyDemand.getStartTimeInMinutes() + random.nextInt(0, maximumAdjustment) * 15);
            }
            // Case 2: ShiftGO starts within the demand window — mutate its start time
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
