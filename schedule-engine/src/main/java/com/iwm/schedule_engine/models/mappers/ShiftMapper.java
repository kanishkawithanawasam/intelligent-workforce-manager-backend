package com.iwm.schedule_engine.models.mappers;

import com.iwm.schedule_engine.models.SchedulesEngineShift;

import java.util.ArrayList;
import java.util.List;

public class ShiftMapper {


    static SchedEngShiftDTO toSchedEngShiftDTO(SchedulesEngineShift shift) {
        SchedEngShiftDTO shiftDTO = new SchedEngShiftDTO();
        shiftDTO.setShiftId(shift.getShiftId());
        shiftDTO.setScheduleId(shift.getScheduleId());
        shiftDTO.setDate(shift.getDate());
        shiftDTO.setEmployee(EmployeeMapper.toSchedEngEmpDTO(shift.getEmployee()));
        shiftDTO.setStartTimeInMinutes(shift.getStartTimeInMinutes());
        shiftDTO.setEndTimeInMinutes(shift.getEndTimeInMinutes());
        return shiftDTO;
    }

    public static List<SchedEngShiftDTO> toSchedEngShiftDTO(List<SchedulesEngineShift> shifts) {
        return new ArrayList<>(shifts.stream().map(ShiftMapper::toSchedEngShiftDTO).toList());
    }

    static SchedulesEngineShift toShift(SchedEngShiftDTO dto) {
        SchedulesEngineShift shift = new SchedulesEngineShift();
        shift.setShiftId(dto.getShiftId());
        shift.setScheduleId(dto.getScheduleId());
        shift.setDate(dto.getDate());
        shift.setEmployee(EmployeeMapper.toEmployee(dto.getEmployee()));
        shift.setStartTimeInMinutes(dto.getStartTimeInMinutes());
        shift.setEndTimeInMinutes(dto.getEndTimeInMinutes());
        return shift;
    }

    public static List<SchedulesEngineShift> toShifts(List<SchedEngShiftDTO> dtos) {
        return new ArrayList<>(dtos.stream().map(ShiftMapper::toShift).toList());
    }
}
