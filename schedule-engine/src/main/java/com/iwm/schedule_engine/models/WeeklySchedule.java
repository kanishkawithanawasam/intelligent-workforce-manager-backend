package com.iwm.schedule_engine.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Weekly schedule chromosome in the schedules pool
 * @version 2
 */
public class WeeklySchedule {

    @Getter
    private final List<Shift> shifts = new ArrayList<>();

    @Getter
    @Setter
    private double fitnessScore;

    private final Map<Employee,List<LocalDate>> employeeDateMap= new HashMap<>();
    private final Map<Employee,Integer> employeeHoursMap= new HashMap<>();

    public WeeklySchedule() {}


    /**
     * Attempts to add a shift to the schedule for a given employee.
     * <p>
     * If the employee already exists in {@code employeeDateMap}, the method will
     * only add the shift if the specified date is already listed for that employee.
     * Otherwise, the shift will not be added and the method returns {@code false}.
     * If the employee does not yet exist in the map, a new entry is created with
     * the given date, and the shift is added successfully.
     * </p>
     *
     * @param shift the {@link Shift} to be added; must not be {@code null}
     * @return {@code true} if the shift was successfully added,
     *         {@code false} if the shift was rejected due to date mismatch
     *
     */
    public boolean addShift(Shift shift) {
        if (this.employeeDateMap.containsKey(shift.getEmployee())) {
            if (this.employeeDateMap.get(shift.getEmployee()).contains(shift.getDate())) {
                return false;
            }else{
                // Verify employee can work in the shift
                int totalMinutesInWeek = this.employeeHoursMap.getOrDefault(shift.getEmployee(),0);
                if (totalMinutesInWeek+shift.getShiftLengthInMinutes()>shift.getEmployee().maxHoursPerWeek()*60)
                    return false;
                this.employeeDateMap.get(shift.getEmployee()).add(shift.getDate());
                this.employeeHoursMap.put(shift.getEmployee(),shift.getShiftLengthInMinutes()+totalMinutesInWeek);
                return this.shifts.add(shift);
            }
        }
        else {
            var dates = new ArrayList<LocalDate>();
            dates.add(shift.getDate());
            this.employeeDateMap.put(shift.getEmployee(), dates);
            return shifts.add(shift);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Schedule fitnessScore=").append(fitnessScore).append("\n");
        for (Shift shift : shifts) {
            builder.append(shift.toString()).append("\n");
        }
        return builder.toString();
    }
}
