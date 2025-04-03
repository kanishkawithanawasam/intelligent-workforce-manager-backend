package com.iwm.backend.api.dtos;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class WeeklyScheduleDTO {

    private Map<LocalDate, List<ShiftDTO>> shifts;

    private double cost;
}
