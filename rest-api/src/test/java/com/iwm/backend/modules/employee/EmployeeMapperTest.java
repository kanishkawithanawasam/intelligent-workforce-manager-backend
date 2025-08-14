package com.iwm.backend.modules.employee;

import com.iwm.backend.modules.preferences.EmployeePreferencesEM;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("EmployeeMapper Test")
class EmployeeMapperTest {

    @Test
    @DisplayName("EmployeeEM to EmployeeDTO")
    void toEmployeeDTO() {

        LocalDate birthday = LocalDate.of(1990, 1, 1);

        EmployeeEM employeeEM = new EmployeeEM();
        employeeEM.setId(1);
        employeeEM.setLastName("John");
        employeeEM.setFirstName("Test");
        employeeEM.setAddress("TestAddress");
        employeeEM.setContact("123456789");
        employeeEM.setPostalCode("12345");
        employeeEM.setDateOfBirth(birthday);

        EmployeeDto employeeDTO = EmployeeMapper.toEmployeeDTO(employeeEM);

        assertEquals(employeeEM.getId(),employeeDTO.getId());
        assertEquals(employeeEM.getLastName(),employeeDTO.getLastName() );
        assertEquals(employeeEM.getFirstName(),employeeDTO.getFirstName());
        assertEquals(employeeEM.getAddress(), employeeDTO.getAddress());
        assertEquals(employeeEM.getContact(),employeeDTO.getContact());
        assertEquals(employeeEM.getPostalCode(),employeeDTO.getPostalCode());
        assertEquals(employeeEM.getDateOfBirth(),birthday);
    }

    @Test
    @DisplayName("EmployeeDTO to EmployeeEM")
    void toEmployeeEM() {

        LocalDate birthday = LocalDate.of(1995, 5, 6);

        EmployeeDto employeeDTO = new EmployeeDto();
        employeeDTO.setId(1);
        employeeDTO.setLastName("John");
        employeeDTO.setFirstName("Test");
        employeeDTO.setAddress("TestAddress");
        employeeDTO.setContact("123456789");
        employeeDTO.setPostalCode("12345");
        employeeDTO.setDateOfBirth(birthday);

        EmployeeEM employeeEM = EmployeeMapper.toEmployeeEM(employeeDTO);

        assertEquals(employeeDTO.getId(),employeeEM.getId());
        assertEquals(employeeDTO.getLastName(),employeeEM.getLastName());
        assertEquals(employeeDTO.getAddress(),employeeEM.getAddress());
        assertEquals(employeeDTO.getContact(), employeeEM.getContact());
        assertEquals(employeeDTO.getPostalCode(), employeeEM.getPostalCode());
        assertEquals(employeeDTO.getDateOfBirth(),birthday);
        assertEquals(employeeDTO.getFirstName(),employeeEM.getFirstName());
    }

    @Test
    @DisplayName("EmployeEM to EmployeeBasicInfoDTO List")
    void toEmployeeForScheduleEngine() {
        LocalDate birthday = LocalDate.of(1995, 5, 6);

        EmployeeEM employeeEM = new EmployeeEM();
        employeeEM.setId(1);
        employeeEM.setLastName("John");
        employeeEM.setFirstName("Test");
        employeeEM.setContact("123456789");
        employeeEM.setPostalCode("12345");
        employeeEM.setDateOfBirth(birthday);

        EmployeePreferencesEM employeePreferencesEM = new EmployeePreferencesEM();
        employeePreferencesEM.setPreferredHours(10.5);
        employeeEM.setPreferences(List.of(employeePreferencesEM));

        SchedEngEmpDTO employee = EmployeeMapper.toEmployeeForScheduleEngine(employeeEM);

        assertEquals(employeeEM.getId(), employee.getId());
        assertEquals(employeeEM.getFirstName()+employeeEM.getLastName(),employee.getName());
        assertEquals(employeeEM.getFirstName()+employeeEM.getLastName(),employee.getName());
        assertEquals(employeeEM.getDateOfBirth(),birthday);
        assertEquals(employeePreferencesEM.getPreferredHours(),employee.getHoursPreference());
        
    }


}