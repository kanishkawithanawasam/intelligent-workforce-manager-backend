package com.iwm.schedule_engine.models;

import java.util.List;

/**
 * Immutable value object that defines information about an employee used by the
 * scheduling engine.
 *
 * <p><strong>Units:</strong> time-based quantities ({@code prefWeeklyHours}, {@code maxHoursPerWeek})
 * are expressed in <em>hours</em> (e.g., {@code 37.5}). The salary field
 * ({@code horulySalary}) represents cost per hour.</p>
 *
 * <h2>Invariants</h2>
 * <ul>
 *   <li>{@code id} must not be {@code null}.</li>
 *   <li>{@code availabilityWindows} must not be {@code null} or empty.</li>
 *   <li>{@code hourlySalary} must be greater than minimum wage</li>
 * </ul>
 *
 * @param id                 unique identifier of the employee (non-{@code null})
 * @param horulySalary       cost metric, i.e., hourly salary/rate (in currency units per hour)
 * @param prefWeeklyHours    employee’s preferred weekly hours (in hours)
 * @param availabilityWindows availability windows provided by the employee (must be non-{@code null} and non-empty)
 * @param maxHoursPerWeek    maximum hours the employee may be scheduled per week (in hours) as allowed by law.
 *
 * @apiNote This record is intended for the scheduling domain and deliberately
 *          omits persistence concerns. Consider using minutes (int) and cents
 *          (int) internally to avoid floating-point rounding if needed.
 *
 * @version 2
 */
public record Employee(
        Long id,
        double horulySalary,
        double prefWeeklyHours,
        List<AvailabilityWindow> availabilityWindows,
        double maxHoursPerWeek
) {

    // @version 2
    public Employee {
        if (id == null) {
            throw new NullPointerException("Id is null");
        }
        if(availabilityWindows == null || availabilityWindows.isEmpty()) {
            throw new NullPointerException("availabilityWindows is empty");
        }
    }
}
