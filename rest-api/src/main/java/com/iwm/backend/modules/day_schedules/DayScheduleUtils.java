package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftEM;

import java.util.List;
import java.time.temporal.ChronoUnit;

/**
 * Utility class providing helper methods for day schedule operations.
 * Contains methods for calculating various metrics related to day schedules.
 */
public class DayScheduleUtils {


    /**
     * Calculates the total cost (in hours) for a list of shifts.
     * The cost is calculated by summing up the duration of each shift in hours.
     *
     * @param shiftEMList List of shifts to calculate the cost for
     * @return Total duration of all shifts in hours
     */
    public static double calculateCost(List<ShiftEM> shiftEMList) {
        double sum = 0;
        for (ShiftEM shiftEM : shiftEMList) {
            // Calculate duration in minutes and convert to hours by dividing by 60
            sum += (ChronoUnit.MINUTES.between(shiftEM.getStartTime(), shiftEM.getEndTime()) / 60.0);
        }
        return sum;
    }
}
