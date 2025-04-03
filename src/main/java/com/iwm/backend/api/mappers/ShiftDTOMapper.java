package com.iwm.backend.api.mappers;

import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.schedulegenerator.models.Shift;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftDTOMapper {

    public static ShiftDTO toDTO(Shift shift) {
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

    public static List<ShiftDTO> toDTO(List<Shift> shifts) {
        return new ArrayList<>(shifts.stream().map(ShiftDTOMapper::toDTO).toList());
    }
}
