package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a daily schedule.
 * This class is used to transfer day schedule information between different layers of the application,
 * containing shifts, associated costs, and peak time information.
 */
@Data
public class DayScheduleDTO {

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
