package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.api.models.ShiftEM;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.schedulegenerator.models.ShiftGO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftDTOMapper {

    public static ShiftDTO toDTO(ShiftGO shift) {
        ShiftDTO dto = new ShiftDTO();
        dto.setStartTime(LocalTime.of(shift.getStartTimeInMinutes()/60,
                shift.getStartTimeInMinutes()%60));
        dto.setEndTime(LocalTime.of(shift.getEndTimeInMinutes()/60,
                shift.getEndTimeInMinutes()%60));
        dto.setDate(shift.getDate());
        dto.setEmployeeId(shift.getEmployee().getId());
        dto.setEmployeeName(shift.getEmployee().getName());
        return dto;
    }

    public static List<ShiftDTO> toDTO(List<ShiftGO> shifts) {
        return new ArrayList<>(shifts.stream().map(ShiftDTOMapper::toDTO).toList());
    }

    public static ShiftEM toShiftEM(ShiftDTO shiftDTO) {

        if (shiftDTO == null) {
            return null;
        }
        ShiftEM shiftEM = new ShiftEM();
        shiftEM.setId(shiftDTO.getShiftId());
        shiftEM.setDate(shiftDTO.getDate());
        shiftEM.setStartTime(shiftDTO.getStartTime());
        shiftEM.setEndTime(shiftDTO.getEndTime());

        EmployeeEM employeeEM = new EmployeeEM();
        employeeEM.setId(shiftDTO.getEmployeeId());
        shiftEM.setEmployee(employeeEM);

        WeeklyScheduleEM weeklyScheduleEM = new WeeklyScheduleEM();

        weeklyScheduleEM.setId(shiftDTO.getScheduleId());
        shiftEM.setWeeklySchedule(weeklyScheduleEM);

        return shiftEM;
    }

    public static ShiftDTO toShiftDTO(ShiftEM shiftEM) {

        if (shiftEM == null) {
            return null;
        }

        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setScheduleId(shiftEM.getWeeklySchedule().getId());
        shiftDTO.setShiftId(shiftEM.getId());
        shiftDTO.setDate(shiftEM.getDate());
        shiftDTO.setStartTime(shiftEM.getStartTime());
        shiftDTO.setEndTime(shiftEM.getEndTime());
        shiftDTO.setEmployeeId(shiftEM.getEmployee().getId());
        shiftDTO.setEmployeeName(shiftEM.getEmployee().getFirstName());
        return shiftDTO;

    }

    public static List<ShiftEM> toShiftEMList(List<ShiftDTO> shiftDTOS) {
        return new ArrayList<>(shiftDTOS.stream().map(ShiftDTOMapper::toShiftEM).toList());
    }

    public static List<ShiftDTO> toShiftDTOList(List<ShiftEM> shiftEMList) {
        return new ArrayList<>(shiftEMList.stream().map(ShiftDTOMapper::toShiftDTO).toList());
    }
}
