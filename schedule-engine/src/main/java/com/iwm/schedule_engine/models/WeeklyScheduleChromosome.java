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
public class WeeklyScheduleChromosome {

    @Getter
    private final List<Shift> shifts = new ArrayList<>();

    @Getter
    @Setter
    private double fitnessScore;

    private final Map<Employee,List<LocalDate>> employeeDateMap= new HashMap<>();

    public WeeklyScheduleChromosome() {}

    public boolean addShift(Shift shift) {
        if (this.employeeDateMap.containsKey(shift.getEmployee())) {
            if (!this.employeeDateMap.get(shift.getEmployee()).contains(shift.getDate())) {
                return false;
            }else{
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
