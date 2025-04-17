package com.iwm.backend.modules.schedules;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyScheduleRequestDTO {
    private LocalDate startDate;
}
