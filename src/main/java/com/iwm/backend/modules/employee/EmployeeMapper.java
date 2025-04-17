package com.iwm.backend.modules.employee;

import java.util.ArrayList;
import java.util.List;

class EmployeeMapper {

     static EmployeeDTO toEmployeeDTO(EmployeeEM employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDateOfBirth(employee.getBirthday());
        dto.setAddress(employee.getAddress());
        dto.setContact(employee.getContact());
        dto.setPostalCode(employee.getPostalCode());
        return dto;
    }

     static EmployeeEM toEmployeeEM(EmployeeDTO dto) {
        EmployeeEM employee = new EmployeeEM();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setBirthday(dto.getDateOfBirth());
        employee.setAddress(dto.getAddress());
        employee.setContact(dto.getContact());
        employee.setPostalCode(dto.getPostalCode());
        return employee;

    }

     static EmployeeForScheduleEngine toEmployeeForScheduleEngine(EmployeeEM employeeEM) {
        EmployeeForScheduleEngine employee = new EmployeeForScheduleEngine();
        employee.setId(employeeEM.getId());
        employee.setName(employeeEM.getFirstName()+employeeEM.getLastName());
        employee.setMaxHoursPerWeek(employee.getMaxHoursPerWeek());
        employee.setHoursPreference(employee.getHoursPreference());
        return employee;
    }

    static List<EmployeeForScheduleEngine> toEmployeeForScheduleEngineList(List<EmployeeEM> employeeEMs) {
         return new ArrayList<>(employeeEMs.stream().map(EmployeeMapper::toEmployeeForScheduleEngine).toList());
    }
}
