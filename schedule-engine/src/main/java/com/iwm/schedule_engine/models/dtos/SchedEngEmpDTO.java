package com.iwm.schedule_engine.models.dtos;

import com.iwm.schedule_engine.models.dtos.interfaces.IScheduleEngineEmpDTO;

public class SchedEngEmpDTO implements IScheduleEngineEmpDTO {

    private long id;
    private String name;
    private String role;
    private double hoursPreference;
    private double maxHoursPerWeek;
    private double cost;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
        return hoursPreference;
    }

    @Override
    public void setHoursPreference(double hoursPreference) {
        this.hoursPreference = hoursPreference;
    }

    @Override
    public double getMaxHoursPerWeek() {
        return maxHoursPerWeek;
    }

    @Override
    public void setMaxHoursPerWeek(double maxHoursPerWeek) {
        this.maxHoursPerWeek = maxHoursPerWeek;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }
}
