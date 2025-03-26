package com.iwm.backend.schedulegenerator.util;

import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.Shift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneticCalculationsUtility{


    /**
     * This function returns the total hours the employee worked in given set of shifts.
     * @param shifts The shifts used to calculate the total number of hours
     * @return The number of hours the employee has worked.
     */
    public static Map<Employee,Double> countTotalHours(List<Shift> shifts){
        Map<Employee,Double> totalHours = new HashMap<>();
        for (Shift shift : shifts) {
            totalHours.put(shift.getEmployee(),
                    totalHours.getOrDefault(
                            shift.getEmployee(),
                            0.0)+
                            calculateTotalHours(shift.getStartTimeInMinutes(), shift.getEndTimeInMinutes()));
        }
        return totalHours;
    }

    private static double calculateTotalHours(int statTimeInMinutes, int endTimeInMinutes) {
        return (endTimeInMinutes-statTimeInMinutes)/60.0;
    }

}
