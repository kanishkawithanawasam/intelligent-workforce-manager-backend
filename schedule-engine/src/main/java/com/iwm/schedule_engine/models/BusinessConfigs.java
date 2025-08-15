package com.iwm.schedule_engine.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * Immutable configuration for the scheduling engine.
 *
 * <p><strong>Units:</strong> shift lengths are in <em>hours</em> (may be fractional, e.g. {@code 7.5}).</p>
 *
 * <h2>Invariants</h2>
 * <ul>
 *   <li>{@code minimumEmployeesPerShift >= 0}</li>
 *   <li>{@code minimumHoursPerShift > 0}</li>
 *   <li>{@code maximumHoursPerShift >= minimumHoursPerShift}</li>
 *   <li>{@code businessOpeningTime < businessClosingTime} (same-day window)</li>
 *   <li>{@code businessClosedDays} use ISO-8601 numbers: {@code 1=Mon … 7=Sun}</li>
 * </ul>
 *
 * <p>If you operate overnight (e.g., 22:00–02:00), model it as two windows
 * (22:00–24:00 and 00:00–02:00) or extend the model to support overnight ranges.</p>
 *
 * @param minimumEmployeesPerShift minimum on-duty headcount required for any shift
 * @param maximumHoursPerShift     maximum shift length in hours (≥ {@code minimumHoursPerShift})
 * @param minimumHoursPerShift     minimum shift length in hours (> 0)
 * @param employeeList             employees eligible for scheduling (defensively copied)
 * @param businessOpeningTime      local opening time (inclusive)
 * @param businessClosingTime      local closing time (exclusive); must be after opening time on the same day
 * @param businessClosedDates      absolute calendar dates when the business is closed (defensively copied)
 * @param businessClosedDays       weekly closed days as ISO day numbers (1=Mon … 7=Sun; defensively copied)
 */
public record BusinessConfigs(
        int minimumEmployeesPerShift,
        double maximumHoursPerShift,
        double minimumHoursPerShift,
        List<Employee> employeeList,
        LocalTime businessOpeningTime,
        LocalTime businessClosingTime,
        Set<LocalDate> businessClosedDates,
        Set<Integer> businessClosedDays
) {
}
