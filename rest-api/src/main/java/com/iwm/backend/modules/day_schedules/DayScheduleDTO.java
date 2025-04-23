package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DayScheduleDTO{

    private List<ShiftDTO> shifts;
    private double cost;
    private LocalTime nextPeak;
}
