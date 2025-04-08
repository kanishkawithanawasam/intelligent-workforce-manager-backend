package com.iwm.backend.api.dtos;


import lombok.Data;

import java.time.LocalDate;
import java.util.TreeMap;

@Data
public class WeeklyScheduleDTO {

    private LocalDate scheduleStartDate;

    private TreeMap<ScheduleEmployeeDTO, ShiftDTO> shifts;

    private double cost;
}
