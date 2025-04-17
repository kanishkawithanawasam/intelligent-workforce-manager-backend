package com.iwm.backend.modules.schedules;

import com.iwm.backend.modules.shift.ShiftDTOMapper;
import com.iwm.backend.modules.shift.ShiftDTO;
import com.iwm.backend.modules.shift.ShiftEM;
import com.iwm.schedule_engine.models.dots.interfaces.IScheduleEngineWeklySchedDTO;

import java.time.LocalDate;
import java.util.*;

public class WeeklyScheduleDTOMapper {



    public static WeeklyScheduleDTO toWeeklyScheduleDTO(IScheduleEngineWeklySchedDTO weeklySchedule) {

        if (weeklySchedule.getShifts() == null) {
            return null;
        }

        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
        List<ShiftDTO> shiftDTOs = ShiftDTOMapper.toShiftDTO(weeklySchedule.getShifts());
        dto.setScheduleStartDate(LocalDate.now());
        dto.setShifts(shiftDTOs);

        return dto;
    }




    public static WeeklyScheduleEM toWeeklyScheduleEM(WeeklyScheduleDTO weeklyScheduleDTO) {

        if (weeklyScheduleDTO == null) {
            return null;
        }

        WeeklyScheduleEM weeklyScheduleEM = new WeeklyScheduleEM();
        weeklyScheduleEM.setShifts(ShiftDTOMapper.toShiftEMList(weeklyScheduleDTO.getShifts()));
        weeklyScheduleEM.setScheduleStartDate(weeklyScheduleDTO.getScheduleStartDate());
        weeklyScheduleEM.setId(weeklyScheduleDTO.getScheduleId());

        for(ShiftEM shiftEM : weeklyScheduleEM.getShifts()) {
            shiftEM.setWeeklySchedule(weeklyScheduleEM);
        }
        return weeklyScheduleEM;
    }



    public static WeeklyScheduleDTO toWeeklyScheduleDTO(WeeklyScheduleEM weeklyScheduleEM) {

        if (weeklyScheduleEM == null) {
            return null;
        }

        WeeklyScheduleDTO weeklyScheduleDTO = new WeeklyScheduleDTO();
        weeklyScheduleDTO.setScheduleStartDate(weeklyScheduleEM.getScheduleStartDate());
        weeklyScheduleDTO.setShifts(ShiftDTOMapper.toShiftDTOList(weeklyScheduleEM.getShifts()));
        for (ShiftDTO shiftDTO : weeklyScheduleDTO.getShifts()) {
            shiftDTO.setScheduleId(weeklyScheduleEM.getId());
        }
        weeklyScheduleDTO.setScheduleStartDate(weeklyScheduleEM.getScheduleStartDate());
        weeklyScheduleDTO.setScheduleId(weeklyScheduleEM.getId());
        return weeklyScheduleDTO;
    }
}
