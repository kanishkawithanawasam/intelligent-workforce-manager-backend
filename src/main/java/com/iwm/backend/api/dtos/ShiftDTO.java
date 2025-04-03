package com.iwm.backend.api.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShiftDTO{

    private long employeeId;

    private String employeeName;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    public ShiftDTO(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShiftDTO() {}

}
