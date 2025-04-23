package com.iwm.schedule_engine.models.dtos;

import com.iwm.schedule_engine.models.dtos.interfaces.IScheduleEngineWeklySchedDTO;
import lombok.Setter;

import java.util.List;

@Setter
public class SchedEngWeklySchedDTO implements IScheduleEngineWeklySchedDTO {

    private List<SchedEngShiftDTO> shifts;

    @Override
    public List<SchedEngShiftDTO> getShifts() {
        return shifts;
    }

}
