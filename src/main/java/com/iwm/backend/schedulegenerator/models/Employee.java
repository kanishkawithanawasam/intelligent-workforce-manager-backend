package com.iwm.backend.schedulegenerator.models;

import java.util.Random;

public class Employee{
    private int id;
    private String name;
    private String role;
    private double weeklyHoursPreference;
    private double maxWeeklyHours;
    private double cost;
    private Random rand = new Random();


    public Employee(int id, String name, String role, double weeklyHoursPreference, double maxWeeklyHours) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.weeklyHoursPreference = weeklyHoursPreference;
        this.maxWeeklyHours = maxWeeklyHours;
        this.cost = rand.nextDouble(10.00,20.00);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getWeeklyHoursPreference() {
        return weeklyHoursPreference;
    }

    public void setWeeklyHoursPreference(double weeklyHoursPreference) {
        this.weeklyHoursPreference = weeklyHoursPreference;
    }

    public double getMaxWeeklyHours() {
        return maxWeeklyHours;
    }
    public void setMaxWeeklyHours(double maxWeeklyHours) {
        this.maxWeeklyHours = maxWeeklyHours;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("Name: %s | Role: %s | WeeklyHoursPreference: %f ", name, role, weeklyHoursPreference);
    }
}
