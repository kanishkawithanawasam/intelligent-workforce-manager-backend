package com.iwm.schedule_engine.models;

import java.util.List;

/**
 * Defines information about an employee.
 * @param id Unique ID of the employee
 * @param cost Cost metric. i.e cost per hour
 * @param prefWeeklyHours Preferred Hours per week
 * @param availabilityWindows Availability windows as provided by the employee.
 */
public record Employee(
        Long id,
        double cost,
        double prefWeeklyHours,
        List<AvailabilityWindow> availabilityWindows
) {
}
