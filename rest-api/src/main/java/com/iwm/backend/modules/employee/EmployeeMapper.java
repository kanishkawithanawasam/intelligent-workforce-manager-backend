package com.iwm.backend.modules.employee;

import com.iwm.schedule_engine.models.Employee;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between different employee-related data transfer objects
 * and entity models.
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.ERROR)
interface EmployeeMapperTest{
    EmployeeDto toDto(EmployeeEM employeeEM);
    EmployeeEM toEmployeeEM(EmployeeDto employeeDto);
}

class EmployeeMapper {

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
