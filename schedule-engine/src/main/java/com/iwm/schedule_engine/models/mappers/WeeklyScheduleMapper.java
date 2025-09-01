package com.iwm.schedule_engine.models.mappers;

import com.iwm.schedule_engine.models.Shift;
import com.iwm.schedule_engine.models.WeeklySchedule;

import java.util.List;

public class WeeklyScheduleMapper {


    public static SchedEngWeklySchedDTO toSchedEngWeklySchedDTO(WeeklySchedule weeklyScheduleChromosome) {
        SchedEngWeklySchedDTO dto = new SchedEngWeklySchedDTO();
        List<SchedEngShiftDTO> shiftDTOs = ShiftMapper.toSchedEngShiftDTO(weeklyScheduleChromosome.getShifts());
        dto.setShifts(shiftDTOs);
        System.out.println(dto.getShifts().size());
        return dto;
    }

    public static WeeklySchedule toWeeklySchedule(SchedEngWeklySchedDTO dto) {
        WeeklySchedule weeklyScheduleChromosome = new WeeklySchedule();
        List<Shift> shifts = ShiftMapper.toShifts(dto.getShifts());
        weeklyScheduleChromosome.setShifts(shifts);
        return weeklyScheduleChromosome;
    }


}
