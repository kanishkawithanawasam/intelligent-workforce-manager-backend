package com.iwm.backend.api.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleRequestDTO {
    private LocalDate startDate;
}
