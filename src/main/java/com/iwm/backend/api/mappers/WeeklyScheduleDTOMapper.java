package com.iwm.backend.api.mappers;

import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyScheduleDTOMapper {

    public static WeeklyScheduleDTO toDTO(WeeklySchedule weeklySchedule) {
        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
        Map<LocalDate, List<ShiftDTO>> shiftsMap = new HashMap<>();

        List<ShiftDTO> shiftDTOs = ShiftDTOMapper.toDTO(weeklySchedule.getShifts());
        for (ShiftDTO shiftDTO : shiftDTOs) {
            if (shiftsMap.containsKey(shiftDTO.getDate())) {
                shiftsMap.get(shiftDTO.getDate()).add(shiftDTO);
            }else{
                List<ShiftDTO> shifts = new ArrayList<>();
                shifts.add(shiftDTO);
                shiftsMap.put(shiftDTO.getDate(), shifts);
            }
        }

        dto.setShifts(shiftsMap);
        return dto;
    }
}
