package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.models.ShiftEM;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;

import java.time.LocalDate;
import java.util.*;

public class WeeklyScheduleDTOMapper {



    public static WeeklyScheduleDTO toWeeklyScheduleDTO(WeeklySchedule weeklySchedule) {

        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
        if (weeklySchedule.getShifts() == null) {
            return null;
        }
        List<ShiftDTO> shiftDTOs = ShiftDTOMapper.toDTO(weeklySchedule.getShifts());
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
        weeklyScheduleDTO.setScheduleStartDate(weeklyScheduleEM.getScheduleStartDate());
        return weeklyScheduleDTO;
    }
}
