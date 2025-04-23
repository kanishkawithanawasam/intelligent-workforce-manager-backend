package com.iwm.schedule_engine.models.mappers;

import com.iwm.schedule_engine.models.Employee;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Employee Mapper Test")
class EmployeeMapperTest {

    @Test
    @DisplayName("Employee Mapper: to Employee test")
    void employeeMapper(){
        SchedEngEmpDTO emp = new SchedEngEmpDTO();
        emp.setId(1);
        emp.setName("John");
        emp.setRole("Manager");
        emp.setHoursPreference(1.0);
        emp.setMaxHoursPerWeek(10.0);
        emp.setCost(10.0);

        Employee outEmp = EmployeeMapper.toEmployee(emp);
        Assertions.assertEquals(emp.getId(), outEmp.getId(), "Employee ID must match!");
        Assertions.assertEquals(emp.getName(), outEmp.getName(), "Employee Name must match!");
        Assertions.assertEquals(emp.getRole(), outEmp.getRole(), "Employee Role must match!");
        Assertions.assertEquals(emp.getHoursPreference(),
                outEmp.getHoursPreference(), "Employee Hours Preference must match!");
        Assertions.assertEquals(emp.getMaxHoursPerWeek(),
                outEmp.getMaxHoursPerWeek(), "Employee Max Hours Per Week must match!");
        Assertions.assertEquals(emp.getCost(), outEmp.getCost(), "Employee Cost must match!");
    }

}