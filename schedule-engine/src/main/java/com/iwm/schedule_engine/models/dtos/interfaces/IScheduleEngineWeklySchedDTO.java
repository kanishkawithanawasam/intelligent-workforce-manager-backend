package com.iwm.schedule_engine.models.dtos.interfaces;

import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;

import java.util.List;

public interface IScheduleEngineWeklySchedDTO {

    List<SchedEngShiftDTO> getShifts();
}
