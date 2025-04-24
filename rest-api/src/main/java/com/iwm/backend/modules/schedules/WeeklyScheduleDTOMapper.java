package com.iwm.backend.modules.schedules;

import com.iwm.backend.modules.shift.ShiftDTOMapper;
import com.iwm.backend.modules.shift.ShiftDTO;
import com.iwm.backend.modules.shift.ShiftEM;
import com.iwm.schedule_engine.models.dtos.interfaces.IScheduleEngineWeklySchedDTO;

import java.time.LocalDate;
import java.util.*;

/**
 * Mapper class responsible for converting between different representations of weekly schedules:
 * WeeklyScheduleDTO, WeeklyScheduleEM and IScheduleEngineWeklySchedDTO.
 */
public class WeeklyScheduleDTOMapper {


    /**
     * Converts a schedule engine weekly schedule DTO to WeeklyScheduleDTO.
     *
     * @param weeklySchedule The schedule engine weekly schedule to convert
     * @return A new WeeklyScheduleDTO object, or null if input shifts are null
     */
    public static WeeklyScheduleDTO toWeeklyScheduleDTO(IScheduleEngineWeklySchedDTO weeklySchedule) {

        if (weeklySchedule.getShifts() == null) {
            return null;
        }

        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
        List<ShiftDTO> shiftDTOs = ShiftDTOMapper.toShiftDTOList(weeklySchedule.getShifts());
        dto.setScheduleStartDate(LocalDate.now());
        dto.setShifts(shiftDTOs);

        return dto;
    }


    /**
     * Converts a WeeklyScheduleDTO to a WeeklyScheduleEM entity.
     * Sets up bidirectional relationships between the schedule and its shifts.
     *
     * @param weeklyScheduleDTO The DTO to convert
     * @return A new WeeklyScheduleEM entity, or null if input is null
     */
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


    /**
     * Converts a WeeklyScheduleEM entity to a WeeklyScheduleDTO.
     * Ensures all shifts reference their parent schedule ID.
     *
     * @param weeklyScheduleEM The entity to convert
     * @return A new WeeklyScheduleDTO object, or null if input is null
     */
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
