package com.iwm.backend.api.dtos;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@Data
public class WeeklyScheduleDTO {

    private LocalDate scheduleStartDate;

    private List<ShiftDTO> shifts;

    private double cost;
}
