package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.configurations.HSOConfigs;
import com.iwm.backend.schedulegenerator.models.*;

import java.time.LocalDate;
import java.util.*;

public class HourlyScheduleOptimiser{


    private RealTimeSchedule getOptimisedRealTimeSchedule(
            WeeklySchedule weeklySchedule,
            HourlyDemand demand,
            LocalDate date,
            HSOConfigs hsoConfigs
    ) {

        List<RealTimeSchedule> population = generatePopulation(
                weeklySchedule,
                demand,
                date,
                hsoConfigs
        );

        return null;

    }


    private double getFitnessScore(RealTimeSchedule realTimeSchedule) {

        return 1/(getTotalViolationsPenalty(realTimeSchedule)+
                getTotalDeviationsPenalty(realTimeSchedule));
    }

    /**
     * Calculates a penalty for deviations from preferences. Currently, deviations are calculated by
     * totalDeviationPenalty = | Total worked hours - Preferred working hours |.
     * @param realTimeSchedule The schedule for the demand period.
     * @return Penalty for total deviations.
     */
    private double getTotalDeviationsPenalty(RealTimeSchedule realTimeSchedule) {
        Map<Employee,Double> weeklyWorkedHours = new HashMap<>();

        for (Shift shift : realTimeSchedule.getShifts()) {
            weeklyWorkedHours.put(
                    shift.getEmployee(),
                    weeklyWorkedHours.getOrDefault(shift.getEmployee(), 0.0)+
                            shift.getShiftDuration());
        }
        double totalDeviationsPenalty = 0;
        for(Employee employee: weeklyWorkedHours.keySet()) {
            totalDeviationsPenalty +=
                     Math.abs(weeklyWorkedHours.get(employee)-employee.getHoursPreference());
        }

        return totalDeviationsPenalty;
    }


    /**
     * Calculates a penalty based on how a particular shift violates given constraints. Currently, the function
     * adds a penalty of 10 for each set of shifts that exceeds an employee's maximum working hours.
     * @param realTimeSchedule The adjustable schedule during the demand period.
     * @return A penalty for total violations.
     */
    private double getTotalViolationsPenalty(RealTimeSchedule realTimeSchedule) {
        Map<Employee,Double> weeklyWorkedHours = new HashMap<>();

        for (Shift shift : realTimeSchedule.getShifts()) {
            weeklyWorkedHours.put(
                    shift.getEmployee(),
                    weeklyWorkedHours.getOrDefault(shift.getEmployee(), 0.0)+
                            shift.getShiftDuration());
        }

        int totalViolations = 0;
        for (Employee employee : weeklyWorkedHours.keySet()) {
            if (weeklyWorkedHours.get(employee) > employee.getMaxHoursPerWeek()) {
                totalViolations+=10;
            }
        }

        return totalViolations;
    }


    public List<RealTimeSchedule> generatePopulation(WeeklySchedule weeklySchedule,
                                                     HourlyDemand demand,
                                                     LocalDate date,
                                                     HSOConfigs hsoConfigs) {


        List<RealTimeSchedule> population = new ArrayList<>();

        // Select shifts ends/starts during the demand period.
        RealTimeSchedule currentDaySchedule = new RealTimeSchedule();
        for (Shift shift : weeklySchedule.getShifts()) {
            if (shiftStartInDmdPeriod(shift,demand)==-1 ||
                shiftStartInDmdPeriod(shift,demand)==1){
                currentDaySchedule.getShifts().add(shift);
            }
        }

        // Creates a random population
        Random random = new Random();
        int maximumAdjustment = (demand.getEndTimeInMinutes()-demand.getStartTimeInMinutes())/15;
        for (int i = 0; i < hsoConfigs.getPopulationSize(); i++) {
            RealTimeSchedule rs = new RealTimeSchedule();
            for(Shift shift : currentDaySchedule.getShifts()) {
                Shift temp = shift.clone();
                if (shiftStartInDmdPeriod(temp,demand)==-1){
                    temp.setEndTimeInMinutes(
                            demand.getStartTimeInMinutes()+random.nextInt(0,maximumAdjustment)*15);
                }else if (shiftStartInDmdPeriod(shift,demand)==1){
                    temp.setStartTimeInMinutes(
                            demand.getEndTimeInMinutes()-random.nextInt(0,maximumAdjustment)*15);
                }
                rs.getShifts().add(temp);
            }
            population.add(rs);
        }

        return population;
    }


    /**
     * Determines whether a given shift starts or ends within the given demand timeframe .
     * @param shift A shift in the schedule.
     * @param demand The new demand for the selected period.
     * @return {@code -1} if the shift ends in demand period. {@code 1} if the shift starts in demand period.
     * Otherwise {@code 0}.
     */
    private int shiftStartInDmdPeriod(Shift shift, HourlyDemand demand) {

        if(demand.getStartTimeInMinutes()< shift.getEndTimeInMinutes() &&
                shift.getEndTimeInMinutes() < demand.getEndTimeInMinutes()*60){
            return -1;
        }

        if(demand.getEndTimeInMinutes()*60 < shift.getStartTimeInMinutes() &&
                shift.getStartTimeInMinutes() < demand.getEndTimeInMinutes()*60){
            return 1;
        }
        return 0;
    }
}
