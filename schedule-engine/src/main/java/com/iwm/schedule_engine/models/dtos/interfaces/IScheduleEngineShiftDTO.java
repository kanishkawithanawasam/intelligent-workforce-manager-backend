package com.iwm.schedule_engine.models.dtos.interfaces;

import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;

import java.time.LocalDate;

public interface IScheduleEngineShiftDTO {
    int getStartTimeInMinutes();
    void setStartTimeInMinutes(int startTimeInMinutes);

    int getEndTimeInMinutes();
    void setEndTimeInMinutes(int endTimeInMinutes);

    SchedEngEmpDTO getEmployee();
    void setEmployee(SchedEngEmpDTO employee);

    LocalDate getDate();
    void setDate(LocalDate date);
}
