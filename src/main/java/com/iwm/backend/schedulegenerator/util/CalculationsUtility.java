package com.iwm.backend.schedulegenerator.util;

import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.ShiftGO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for performing common schedule-related calculations
 * such as computing total working hours for employees.
 */
public class CalculationsUtility {


    /**
     * Computes the total number of hours worked by each employee
     * across a given list of shifts.
     *
     * <p>This method iterates through the list of shifts and accumulates
     * the duration (in hours) of each shift per employee.</p>
     *
     * @param shifts the list of shifts used to calculate hours worked
     * @return a map of employees to their total hours worked
     */
    public static Map<Employee,Double> countTotalHours(List<ShiftGO> shifts){
        Map<Employee,Double> totalHours = new HashMap<>();
        for (ShiftGO shift : shifts) {
            totalHours.put(shift.getEmployee(),
                    totalHours.getOrDefault(
                            shift.getEmployee(),
                            0.0)+
                            calculateTotalHours(shift.getStartTimeInMinutes(), shift.getEndTimeInMinutes()));
        }
        return totalHours;
    }

    /**
     * Calculates the duration of a shift in hours, given its start and end time in minutes.
     *
     * @param statTimeInMinutes the start time of the shift in minutes
     * @param endTimeInMinutes   the end time of the shift in minutes
     * @return the duration of the shift in hours as a decimal value
     */
    private static double calculateTotalHours(int statTimeInMinutes, int endTimeInMinutes) {
        return (endTimeInMinutes-statTimeInMinutes)/60.0;
    }

}
