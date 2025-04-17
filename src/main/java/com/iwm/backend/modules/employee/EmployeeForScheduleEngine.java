package com.iwm.backend.modules.employee;


import com.iwm.schedule_engine.models.interfaces.ScheduleEngineEmployeeDTOInterface;

public class EmployeeForScheduleEngine implements ScheduleEngineEmployeeDTOInterface {

    private long employeeId;
    private String employeeName;
    private String role;
    private double maxHoursPerWeek;
    private double costPerHour;
    private double preferredHoursPerWeek;

    @Override
    public long getId() {
        return employeeId;
    }

    @Override
    public void setId(long id) {
        this.employeeId = id;
    }

    @Override
    public String getName() {
        return employeeName;
    }

    @Override
    public void setName(String name) {
        this.employeeName = name;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public double getHoursPreference() {
        return preferredHoursPerWeek;
    }

    @Override
    public void setHoursPreference(double hoursPreference) {
        this.preferredHoursPerWeek = hoursPreference;
    }

    @Override
    public double getMaxHoursPerWeek() {
        return this.maxHoursPerWeek;
    }

    @Override
    public void setMaxHoursPerWeek(double maxHoursPerWeek) {
        this.maxHoursPerWeek = maxHoursPerWeek;
    }

    @Override
    public double getCost() {
        return costPerHour;
    }

    @Override
    public void setCost(double cost) {
        this.costPerHour = cost;
    }
}
