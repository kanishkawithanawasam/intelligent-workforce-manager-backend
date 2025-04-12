package com.iwm.backend.api.dtos;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@Data
public class WeeklyScheduleDTO {

    private LocalDate scheduleStartDate;

    private long scheduleId;

    private List<ShiftDTO> shifts;

    private double cost;

    public WeeklyScheduleDTO(LocalDate scheduleStartDate, List<ShiftDTO> shifts, double cost) {
        this.scheduleStartDate = scheduleStartDate;
        this.shifts = shifts;
        this.cost = cost;
    }

    public WeeklyScheduleDTO() {
    }
}
