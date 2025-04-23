package com.iwm.schedule_engine.models.dtos;

import com.iwm.schedule_engine.models.dtos.interfaces.IScheduleEngineShiftDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class SchedEngShiftDTO implements IScheduleEngineShiftDTO {

    @Getter
    @Setter
    private long shiftId;
    @Getter
    @Setter
    private long scheduleId;
    private int startTimeInMinutes;
    private int endTimeInMinutes;
    private SchedEngEmpDTO employee;
    private LocalDate date;


    @Override
    public int getStartTimeInMinutes() {
        return this.startTimeInMinutes;
    }

    @Override
    public void setStartTimeInMinutes(int startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }

    @Override
    public int getEndTimeInMinutes() {
        return this.endTimeInMinutes;
    }

    @Override
    public void setEndTimeInMinutes(int endTimeInMinutes) {
        this.endTimeInMinutes = endTimeInMinutes;
    }

    @Override
    public SchedEngEmpDTO getEmployee() {
        return employee;
    }

    @Override
    public void setEmployee(SchedEngEmpDTO employee) {
        this.employee = employee;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
