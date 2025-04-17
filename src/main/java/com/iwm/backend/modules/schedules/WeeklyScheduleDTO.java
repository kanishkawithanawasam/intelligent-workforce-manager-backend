package com.iwm.backend.modules.schedules;


import com.iwm.backend.modules.shift.ShiftDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
