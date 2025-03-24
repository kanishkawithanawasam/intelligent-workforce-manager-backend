package com.iwm.backend.schedulegenerator.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the system.
 * @author kanishka withanawasam
 * @version 1.0
 */
public class Employee{
    private int id;
    private String name;
    private String role;
    private double hoursPreference;
    private final double maxHoursPerWeek;
    private final double cost;
    private final List<String> currentWorkingDays;
    private double totalWorkedHours;


    /**
     *
     * @param id Id of the employee.
     * @param name Name of the employee.
     * @param role Role of the employee.
     * @param hoursPreference Number of hours the employee would like to work.
     * @param maxHoursPerWeek The maximum number of hours employee is allowed to work.
     */
    public Employee(int id, String name, String role, double hoursPreference, double maxHoursPerWeek, double cost) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.hoursPreference = hoursPreference;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.currentWorkingDays = new ArrayList<String>();
        this.totalWorkedHours = 0;
        this.cost=cost;
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

    public List<String> getCurrentWorkingDays() {
        return currentWorkingDays;
    }

    public double getTotalWorkedHours() {
        return totalWorkedHours;
    }

    /**
     * Set total worked duration in hours
     * @param totalWorkedHours Worked duration in hours during the week.
     */
    public void setTotalWorkedHours(double totalWorkedHours) {
        this.totalWorkedHours = totalWorkedHours;
    }

    /**
     * Determines if this employee can work in the given day for the given hours.
     * @param date The date of the new shift to be assigned.
     * @param shiftLength  The length of the shift in hours
     * @return {@code true} if the employee is available. Otherwise, the function will return {@code false}.
     */
    public boolean isAvailable(String date,double shiftLength) {
        return !(totalWorkedHours + shiftLength > maxHoursPerWeek) && !currentWorkingDays.contains(date);
    }

    @Override
    public String toString() {
        return String.format("Name: %s | Role: %s | WeeklyHoursPreference: %f | Maximum hours allowed: %f | "
                +"Total hours worked: %f ",
                name, role, hoursPreference, maxHoursPerWeek, totalWorkedHours);
    }
}
