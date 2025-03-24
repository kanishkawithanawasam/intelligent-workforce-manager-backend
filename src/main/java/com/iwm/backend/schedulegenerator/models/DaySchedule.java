package com.iwm.backend.schedulegenerator.models;

import java.util.ArrayList;
import java.util.List;

public class DaySchedule {

    private String date;

    private List<Shift> shifts = new ArrayList<>();

    public DaySchedule(WeeklySchedule weeklySchedule, String date) {
        this.date = date;
        for (Shift shift : weeklySchedule.getShifts()) {
            if (shift.getDate().equals(date)) {
                shifts.add(shift);
            }
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
}
