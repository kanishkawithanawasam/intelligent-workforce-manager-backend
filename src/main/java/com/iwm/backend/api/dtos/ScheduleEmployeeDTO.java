package com.iwm.backend.api.dtos;

import lombok.Data;

@Data
public class ScheduleEmployeeDTO implements Comparable<ScheduleEmployeeDTO> {

    private long employeeId;
    private String employeeName;

    @Override
    public int compareTo(ScheduleEmployeeDTO otherEmployeeDTO) {
        return Long.compare(this.employeeId, otherEmployeeDTO.employeeId);
    }
}
