package com.iwm.schedule_engine.models.mappers;

import com.iwm.schedule_engine.models.Employee;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {


    public static Employee toEmployee(SchedEngEmpDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setCost(employeeDTO.getCost());
        employee.setRole(employeeDTO.getRole());
        employee.setHoursPreference(employeeDTO.getHoursPreference());
        employee.setMaxHoursPerWeek(employeeDTO.getMaxHoursPerWeek());
        return employee;
    }

    public static SchedEngEmpDTO toSchedEngEmpDTO(Employee employee) {
        SchedEngEmpDTO employeeDTO = new SchedEngEmpDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setCost(employee.getCost());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setHoursPreference(employee.getHoursPreference());
        employeeDTO.setMaxHoursPerWeek(employee.getMaxHoursPerWeek());
        return employeeDTO;
    }

    public static List<Employee> toEmployees(List<SchedEngEmpDTO> employeeDTOs) {
        return new ArrayList<>(employeeDTOs.stream().map(EmployeeMapper::toEmployee).toList());
    }

}
