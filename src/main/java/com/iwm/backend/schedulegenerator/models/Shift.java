package com.iwm.backend.schedulegenerator.models;

import java.time.LocalDate;

public class Shift implements Cloneable{

    private int startTimeInMinutes;
    private int endTimeInMinutes;
    private Employee employee;
    private final LocalDate date;
    private final String type;
    private final double shiftDuration;

    public Shift(LocalDate date, int startTimeInMinutes, int endTimeInMinutes,
                 Employee employee, String type) {
        this.date = date;
        this.endTimeInMinutes = endTimeInMinutes;
        this.startTimeInMinutes = startTimeInMinutes;
        this.employee = employee;
        this.type = type;
        shiftDuration = (endTimeInMinutes - startTimeInMinutes) / 60.0;
    }

    public Shift(Shift shift) {
        this.date = shift.getDate();
        this.endTimeInMinutes = shift.getEndTimeInMinutes();
        this.startTimeInMinutes = shift.getStartTimeInMinutes();
        this.employee = shift.getEmployee();
        this.type = shift.getType();
        this.shiftDuration = shift.getShiftDuration();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Name: %s |  Date: %s | Start-time: %f | End-time: %f",
                employee.getName(), date, startTimeInMinutes/60.0, endTimeInMinutes/60.0);
    }

    /**
     *
     * @return The shift duration in hours.
     */
    public double getShiftDuration() {
        return shiftDuration;
    }

    public int getStartTimeInMinutes() {
        return startTimeInMinutes;
    }

    public void setStartTimeInMinutes(int startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }

    public int getEndTimeInMinutes() {
        return endTimeInMinutes;
    }

    public void setEndTimeInMinutes(int endTimeInMinutes) {
        this.endTimeInMinutes = endTimeInMinutes;
    }

    public String getStartTime() {
        return String.format("%02d:%02d", startTimeInMinutes/60, startTimeInMinutes%60);
    }

    public String getEndTime() {
        return String.format("%02d:%02d", endTimeInMinutes/60, endTimeInMinutes%60);
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
