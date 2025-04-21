package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class DaySchedule {

    private List<ShiftDTO> shifts;
    private double cost;
    private LocalTime nextPeak;
}
