package com.iwm.backend.schedulegenerator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

    private List<Shift> shifts = new ArrayList<>();
    private Map<String, List<Shift>> shiftTypeMap = new HashMap<>();


    private double fitnessScore;

    public void addShift(Shift shift) {
        this.shifts.add(shift);
        if(shiftTypeMap.get(shift.getType())==null){
            shiftTypeMap.put(shift.getType(),new ArrayList<>());
            shiftTypeMap.get(shift.getType()).add(shift);
        }else{
            shiftTypeMap.get(shift.getType()).add(shift);
        }
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

    public Map<String, List<Shift>> getShiftTypeMap() {
        return shiftTypeMap;
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
