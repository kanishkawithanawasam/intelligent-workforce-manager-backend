package com.iwm.backend.modules.shift;

import com.iwm.backend.modules.employee.EmployeeEM;
import com.iwm.backend.modules.schedules.WeeklyScheduleEM;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftDTOMapper {

    public static ShiftDTO toShiftDTO(SchedEngShiftDTO shift) {
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

    public static List<ShiftDTO> toShiftDTO(List<SchedEngShiftDTO> shifts) {
        return new ArrayList<>(shifts.stream().map(ShiftDTOMapper::toShiftDTO).toList());
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
