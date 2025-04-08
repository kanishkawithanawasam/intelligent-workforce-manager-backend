package com.iwm.backend.api.dtos;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShiftDTO{

    private long employeeId;

    private long shiftId;

    private LocalDate date;

    private String employeeName;

    private LocalTime startTime;

    private LocalTime endTime;

    public ShiftDTO( LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShiftDTO() {}

}
