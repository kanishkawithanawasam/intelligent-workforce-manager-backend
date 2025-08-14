package com.iwm.backend.modules.employee;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps data between data transfer objects and entity objects.
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.ERROR)
interface EmployeeMapperTest{
    EmployeeDto toDto(EmployeeEM employeeEM);
    EmployeeEM toEmployeeEM(EmployeeDto employeeDto);
}
