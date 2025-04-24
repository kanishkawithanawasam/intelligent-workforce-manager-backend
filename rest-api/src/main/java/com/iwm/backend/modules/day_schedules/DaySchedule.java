package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * Represents a daily schedule containing information about shifts, costs, and peak times.
 * This class is used as an internal model for managing daily schedules within the application.
 */
@Data
public class DaySchedule {

    /**
     * List of shifts scheduled for the day.
     * Contains detailed information about each shift including start and end times.
     */
    private List<ShiftDTO> shifts;

    /**
     * Total cost of all shifts in the schedule, measured in hours.
     * Calculated as the sum of all shift durations.
     */
    private double cost;

    /**
     * The next predicted peak time in the schedule.
     * Represents a point in time when high activity or workload is expected.
     */
    private LocalTime nextPeak;
}
