package com.iwm.backend.modules.employee;

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

    public static EmployeeEM toEmployeeEM(EmployeeDTO dto) {
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
}
