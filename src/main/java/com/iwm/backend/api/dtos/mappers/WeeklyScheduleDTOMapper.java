package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.models.ShiftEM;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.schedulegenerator.models.ShiftGO;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;

import java.time.LocalDate;
import java.util.*;

public class WeeklyScheduleDTOMapper {



    public static WeeklyScheduleDTO toWeeklyScheduleDTO(WeeklySchedule weeklySchedule) {

        if (weeklySchedule.getShifts() == null) {
            return null;
        }

        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
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
