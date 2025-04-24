package com.iwm.backend.modules.employee;

import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between different employee-related data transfer objects
 * and entity models.
 */
class EmployeeMapper {

    /**
     * Converts an EmployeeEM entity to an EmployeeDTO.
     *
     * @param employee The employee entity to convert
     * @return The converted EmployeeDTO object
     */
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

    /**
     * Converts an EmployeeDTO to an EmployeeEM entity.
     *
     * @param dto The EmployeeDTO to convert
     * @return The converted EmployeeEM entity
     */
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

    /**
     * Converts an EmployeeEM entity to a SchedEngEmpDTO for schedule engine processing.
     *
     * @param employeeEM The employee entity to convert
     * @return The converted SchedEngEmpDTO object
     */
    static SchedEngEmpDTO toEmployeeForScheduleEngine(EmployeeEM employeeEM) {
        SchedEngEmpDTO employee = new SchedEngEmpDTO();
        employee.setId(employeeEM.getId());
        employee.setName(employeeEM.getFirstName()+employeeEM.getLastName());
        employee.setMaxHoursPerWeek(employee.getMaxHoursPerWeek());
        employee.setHoursPreference(employee.getHoursPreference());
        int prefSide = employeeEM.getPreferences().size();
        employee.setHoursPreference(employeeEM.getPreferences().get(prefSide-1).getPreferredHours());
        return employee;
    }

    /**
     * Converts a list of EmployeeEM entities to a list of SchedEngEmpDTO objects.
     *
     * @param employeeEMs The list of employee entities to convert
     * @return The list of converted SchedEngEmpDTO objects
     */
    static List<SchedEngEmpDTO> toEmployeeForScheduleEngineList(List<EmployeeEM> employeeEMs) {
         return new ArrayList<>(employeeEMs.stream().map(EmployeeMapper::toEmployeeForScheduleEngine).toList());
    }
}
