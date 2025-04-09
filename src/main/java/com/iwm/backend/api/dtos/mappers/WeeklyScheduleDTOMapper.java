package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.dtos.ScheduleEmployeeDTO;
import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.dtos.mappers.exceptions.WeeklyScheduleDTOIsNullException;
import com.iwm.backend.api.dtos.mappers.exceptions.WeeklyScheduleEMIsNullException;
import com.iwm.backend.api.models.ShiftEM;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class WeeklyScheduleDTOMapper {

    public static WeeklyScheduleDTO toDTO(WeeklySchedule weeklySchedule) {

        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
        if (weeklySchedule.getShifts() == null) {
            throw  new RuntimeException();
        }
        List<ShiftDTO> shiftDTOs = ShiftDTOMapper.toDTO(weeklySchedule.getShifts());
        dto.setScheduleStartDate(LocalDate.now());
        dto.setShifts(shiftDTOs);

        return dto;
    }


    private static Set<Long> getEmployeeIds(Set<ScheduleEmployeeDTO> scheduleEmployeeDTOs) {
        Set<Long> employeeIds = new HashSet<>();
        for (ScheduleEmployeeDTO scheduleEmployeeDTO : scheduleEmployeeDTOs) {
            employeeIds.add(scheduleEmployeeDTO.getEmployeeId());
        }
        return employeeIds;
    }

    private static ScheduleEmployeeDTO getScheduleEmployeeDTO(long id, Set<ScheduleEmployeeDTO> scheduleEmployeeDTOs) {
        for (ScheduleEmployeeDTO scheduleEmployeeDTO : scheduleEmployeeDTOs) {
            if (scheduleEmployeeDTO.getEmployeeId() == id) {
                return scheduleEmployeeDTO;
            }
        }
        return null;
    }

    public static WeeklyScheduleEM toWeeklyScheduleEM(WeeklyScheduleDTO weeklyScheduleDTO) {

        if (weeklyScheduleDTO == null) {
            throw new WeeklyScheduleDTOIsNullException();
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
            throw new WeeklyScheduleEMIsNullException();
        }

        WeeklyScheduleDTO weeklyScheduleDTO = new WeeklyScheduleDTO();
        weeklyScheduleDTO.setScheduleStartDate(weeklyScheduleEM.getScheduleStartDate());
        weeklyScheduleDTO.setShifts(ShiftDTOMapper.toShiftDTOList(weeklyScheduleEM.getShifts()));
        LocalDateTime createdDate =  weeklyScheduleEM.getCreateTime();
        weeklyScheduleDTO.setScheduleStartDate(LocalDate.from(createdDate));
        return weeklyScheduleDTO;
    }
}
