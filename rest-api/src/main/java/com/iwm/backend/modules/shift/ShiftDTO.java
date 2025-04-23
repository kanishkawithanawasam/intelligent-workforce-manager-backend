package com.iwm.backend.modules.shift;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShiftDTO{

    private long employeeId;

    private long shiftId;

    private long scheduleId;

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
