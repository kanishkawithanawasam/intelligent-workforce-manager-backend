package com.iwm.schedule_engine.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class Shift implements Cloneable{

    private long shiftId;

    private long scheduleId;

    private int startTimeInMinutes;

    private int endTimeInMinutes;

    private double cost;

    private Employee employee;

    private LocalDate date;

    public Shift(LocalDate date, int startTimeInMinutes, int endTimeInMinutes,
                 Employee employee) {
        this.date = date;
        this.endTimeInMinutes = endTimeInMinutes;
        this.startTimeInMinutes = startTimeInMinutes;
        this.employee = employee;
        this.cost = ((endTimeInMinutes - startTimeInMinutes)/60.0)*employee.getCost();
    }

    public Shift() {
    }

    public int getShiftLengthInMinutes() {
        return endTimeInMinutes - startTimeInMinutes;
    }

    @Override
    public String toString() {
        return String.format("Name: %s| Shift: %d| Schedule: %d |  Date: %s | Start-time: %f | End-time: %f",
                employee.getName(),shiftId,scheduleId, date, startTimeInMinutes/60.0, endTimeInMinutes/60.0);
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
