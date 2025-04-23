package com.iwm.schedule_engine.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Represents an employee in the system.
 * @author kanishka withanawasam
 * @version 1.0
 */
@Getter
@Setter
public class Employee {

    private long id;

    private String name;

    private String role;

    private double hoursPreference;

    private double maxHoursPerWeek;

    private double cost;

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


    @Override
    public String toString() {
        return String.format("Name: %s | Role: %s | WeeklyHoursPreference: %f | Maximum hours allowed: %f | ",
                name, role, hoursPreference, maxHoursPerWeek);
    }

}
