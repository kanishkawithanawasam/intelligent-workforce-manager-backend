package com.iwm.backend.schedulegenerator.models;

public class Shift {

    private final int startTimeInMinutes;
    private final int endTimeInMinutes;
    private Employee employee;
    private final String date;
    private final String type;
    private final double shiftDuration;

    public Shift(String date, int startTimeInMinutes, int endTimeInMinutes,
                 Employee employee, String type) {
        this.date = date;
        this.endTimeInMinutes = endTimeInMinutes;
        this.startTimeInMinutes = startTimeInMinutes;
        this.employee = employee;
        this.type = type;
        shiftDuration = (endTimeInMinutes - startTimeInMinutes) / 60.0;
    }

    public String getDate() {
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

    public String getStartTime() {
        return String.format("%02d:%02d", startTimeInMinutes/60, startTimeInMinutes%60);
    }

    public String getEndTime() {
        return String.format("%02d:%02d", endTimeInMinutes/60, endTimeInMinutes%60);
    }
}
