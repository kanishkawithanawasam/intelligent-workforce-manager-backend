package com.iwm.schedule_engine.models.mappers;

import com.iwm.schedule_engine.models.SchedulesEngineShift;
import com.iwm.schedule_engine.models.WeeklyScheduleChromosome;

import java.util.List;

public class WeeklyScheduleMapper {


    public static SchedEngWeklySchedDTO toSchedEngWeklySchedDTO(WeeklyScheduleChromosome weeklyScheduleChromosome) {
        SchedEngWeklySchedDTO dto = new SchedEngWeklySchedDTO();
        List<SchedEngShiftDTO> shiftDTOs = ShiftMapper.toSchedEngShiftDTO(weeklyScheduleChromosome.getShifts());
        dto.setShifts(shiftDTOs);
        System.out.println(dto.getShifts().size());
        return dto;
    }

    public static WeeklyScheduleChromosome toWeeklySchedule(SchedEngWeklySchedDTO dto) {
        WeeklyScheduleChromosome weeklyScheduleChromosome = new WeeklyScheduleChromosome();
        List<SchedulesEngineShift> shifts = ShiftMapper.toShifts(dto.getShifts());
        weeklyScheduleChromosome.setShifts(shifts);
        return weeklyScheduleChromosome;
    }


}
