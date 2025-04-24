package com.iwm.backend.modules.schedules;


import com.iwm.backend.modules.shift.ShiftDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object representing a weekly schedule.
 * Used for transferring schedule data between different layers of the application.
 */
@Data
public class WeeklyScheduleDTO {

    /**
     * The start date of the weekly schedule.
     */
    private LocalDate scheduleStartDate;

    /**
     * Unique identifier of the schedule.
     */
    private long scheduleId;

    /**
     * List of shifts included in this weekly schedule.
     */
    private List<ShiftDTO> shifts;

    /**
     * Constructs a new WeeklyScheduleDTO with specified start date and shifts.
     *
     * @param scheduleStartDate the start date of the schedule
     * @param shifts            the list of shifts in the schedule
     */
    public WeeklyScheduleDTO(LocalDate scheduleStartDate, List<ShiftDTO> shifts) {
        this.scheduleStartDate = scheduleStartDate;
        this.shifts = shifts;
    }

    /**
     * Default constructor for WeeklyScheduleDTO.
     */
    public WeeklyScheduleDTO() {
    }
}
