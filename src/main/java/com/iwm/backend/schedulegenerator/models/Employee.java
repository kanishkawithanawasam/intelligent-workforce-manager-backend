package com.iwm.backend.schedulegenerator.models;


import java.util.List;

/**
 * Represents an employee in the system.
 * @author kanishka withanawasam
 * @version 1.0
 */
public class Employee{

    private long id;

    private String name;

    private String role;

    private double hoursPreference;

    private double maxHoursPerWeek;

    private double cost;

    private List<Shift> shifts;

    public Employee(){
    }

    /**
     *
     * @param id The ID of the employee.
     * @param name Name of the employee.
     * @param role Role of the employee.
     * @param hoursPreference Number of hours the employee would like to work.
     * @param maxHoursPerWeek The maximum number of hours employee is allowed to work.
     */
    public Employee(long id, String name, String role, double hoursPreference, double maxHoursPerWeek, double cost) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.hoursPreference = hoursPreference;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.cost=cost;
    }

    public long getId() {
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

    public double getHoursPreference() {
        return hoursPreference;
    }

    public void setHoursPreference(double hoursPreference) {
        this.hoursPreference = hoursPreference;
    }

    public double getMaxHoursPerWeek() {
        return maxHoursPerWeek;
    }

    public double getCost() {
        return cost;
    }




    @Override
    public String toString() {
        return String.format("Name: %s | Role: %s | WeeklyHoursPreference: %f | Maximum hours allowed: %f | ",
                name, role, hoursPreference, maxHoursPerWeek);
    }
}
