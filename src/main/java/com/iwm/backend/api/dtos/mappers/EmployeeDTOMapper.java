package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.dtos.employee.EmployeeDTO;
import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.schedulegenerator.models.Employee;

public class EmployeeDTOMapper {

    public static EmployeeDTO toEmployeeDTO(EmployeeEM employee) {
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
}
