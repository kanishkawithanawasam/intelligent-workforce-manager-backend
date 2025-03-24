package com.iwm.backend.schedulegenerator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklySchedule {

    private List<Shift> shifts = new ArrayList<>();
    private Map<String,List<Shift>> empDateMap= new HashMap<>();

    private double fitnessScore;

    public void addShift(Shift shift) {
        if(!empDateMap.containsKey(shift.getDate())){
            List<Shift> shiftList= new ArrayList<>();
            shiftList.add(shift);
            empDateMap.put(shift.getDate(),shiftList);
        }else {
            empDateMap.get(shift.getDate()).add(shift);
        }
        this.shifts.add(shift);
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

    public Map<String, List<Shift>> getEmpDateMap() {
        return empDateMap;
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
