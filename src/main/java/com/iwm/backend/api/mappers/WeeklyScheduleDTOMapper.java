package com.iwm.backend.api.mappers;

import com.iwm.backend.api.dtos.ScheduleEmployeeDTO;
import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;

import java.time.LocalDate;
import java.util.*;

public class WeeklyScheduleDTOMapper {

    public static WeeklyScheduleDTO toDTO(WeeklySchedule weeklySchedule) {

        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
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
}
