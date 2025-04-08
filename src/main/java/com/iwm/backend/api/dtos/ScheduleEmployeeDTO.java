package com.iwm.backend.api.dtos;

public class ScheduleEmployeeDTO implements Comparable<ScheduleEmployeeDTO> {

    private long employeeId;
    private String employeeName;

    @Override
    public int compareTo(ScheduleEmployeeDTO otherEmployeeDTO) {
        return Long.compare(this.employeeId, otherEmployeeDTO.employeeId);
    }
}
