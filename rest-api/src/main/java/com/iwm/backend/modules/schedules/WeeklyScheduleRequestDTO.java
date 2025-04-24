package com.iwm.backend.modules.schedules;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for weekly schedule requests.
 * Used to transfer schedule start date information between client and server.
 */
@Data
public class WeeklyScheduleRequestDTO {
    /**
     * The start date of the weekly schedule.
     * This date represents the beginning of a week for which the schedule is being requested.
     */
    private LocalDate startDate;
}
