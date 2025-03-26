package com.iwm.backend.schedulegenerator.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklySchedule {

    private final List<Shift> shifts = new ArrayList<>();
    private final Map<LocalDate, List<Shift>> shiftDateMap = new HashMap<LocalDate, List<Shift>>();
    private final Map<LocalDate, List<Employee>> empDateMap= new HashMap<LocalDate, List<Employee>>();

    private double fitnessScore;

    public void addShift(Shift shift) {

        if (!empDateMap.containsKey(shift.getDate())) {
            List<Employee> tempEmpList = new ArrayList<>();
            tempEmpList.add(shift.getEmployee());
            empDateMap.put(shift.getDate(),tempEmpList);
        }else{
            empDateMap.get(shift.getDate()).add(shift.getEmployee());
        }

        // Used for JSON bindings
        if(!shiftDateMap.containsKey(shift.getDate())){
            List<Shift> shiftList= new ArrayList<>();
            shiftList.add(shift);
            shiftDateMap.put(shift.getDate(),shiftList);
        }else {
            shiftDateMap.get(shift.getDate()).add(shift);
        }

        this.shifts.add(shift);
    }

    public Map<LocalDate, List<Employee>> getEmpDateMap() {
        return empDateMap;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    // Used for JSON only
    public Map<LocalDate, List<Shift>> getShiftDateMap() {
        return shiftDateMap;
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
