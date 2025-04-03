package com.iwm.backend.schedulegenerator.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Shift implements Cloneable{

    @Setter
    @Getter
    private int startTimeInMinutes;

    @Setter
    @Getter
    private int endTimeInMinutes;

    @Getter
    @Setter
    private Employee employee;

    private WeeklySchedule weeklySchedule;

    @Getter
    private LocalDate date;

    public Shift(LocalDate date, int startTimeInMinutes, int endTimeInMinutes,
                 Employee employee) {
        this.date = date;
        this.endTimeInMinutes = endTimeInMinutes;
        this.startTimeInMinutes = startTimeInMinutes;
        this.employee = employee;
    }

    public Shift() {}


    public int getShiftLengthInMinutes() {
        return endTimeInMinutes - startTimeInMinutes;
    }

    @Override
    public String toString() {
        return String.format("Name: %s |  Date: %s | Start-time: %f | End-time: %f",
                employee.getName(), date, startTimeInMinutes/60.0, endTimeInMinutes/60.0);
    }


    @Override
    public Shift clone() {
        try {
            return (Shift) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
