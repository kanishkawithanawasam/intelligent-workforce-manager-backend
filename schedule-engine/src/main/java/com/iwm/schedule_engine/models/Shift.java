package com.iwm.schedule_engine.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a shift representation used in the scheduling algorithms
 * @version 1
 */
@Getter
@Setter
public class Shift implements Cloneable{

    private int startTimeInMinutes;

    private int endTimeInMinutes;

    private Employee employee;

    private LocalDate date;

    public Shift(LocalDate date, int startTimeInMinutes, int endTimeInMinutes,
                 Employee employee) {
        this.date = date;
        this.endTimeInMinutes = endTimeInMinutes;
        this.startTimeInMinutes = startTimeInMinutes;
        this.employee = employee;
    }

    public Shift() {
    }

    /**
     *
     * @return The shift length in minutes
     */
    public int getShiftLengthInMinutes() {
        return endTimeInMinutes - startTimeInMinutes;
    }

    @Override
    public String toString() {
        return String.format("Employee Id: %d|  Date: %s | Start-time: %f | End-time: %f",
                employee.id(), date, startTimeInMinutes/60.0, endTimeInMinutes/60.0);
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
