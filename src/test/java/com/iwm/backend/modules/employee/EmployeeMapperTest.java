package com.iwm.backend.modules.employee;

import com.iwm.backend.modules.preferences.EmployeePreferencesEM;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Employee Mapper Test")
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
        employeeEM.setBirthday(birthday);

        EmployeeDTO employeeDTO = EmployeeMapper.toEmployeeDTO(employeeEM);

        assertEquals(employeeEM.getId(),
                employeeDTO.getId(), "Employee Id should be equal");
        assertEquals(employeeEM.getLastName(),
                employeeDTO.getLastName(), "Employee Last Name should be equal");
        assertEquals(employeeEM.getFirstName(),
                employeeDTO.getFirstName(),"Employee First Name should be equal");
        assertEquals(employeeEM.getAddress(),
                employeeDTO.getAddress(), "Employee Address should be equal");
        assertEquals(employeeEM.getContact(),
                employeeDTO.getContact(),"Employee Contact should be equal");
        assertEquals(employeeEM.getPostalCode(),
                employeeDTO.getPostalCode(),"Employee Postal Code should be equal");
        assertEquals(employeeEM.getBirthday(),
                birthday,"Employee Date of Birth should be equal");
    }

    @Test
    @DisplayName("EmployeeDTO to EmployeeEM")
    void toEmployeeEM() {

        LocalDate birthday = LocalDate.of(1995, 5, 6);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setLastName("John");
        employeeDTO.setFirstName("Test");
        employeeDTO.setAddress("TestAddress");
        employeeDTO.setContact("123456789");
        employeeDTO.setPostalCode("12345");
        employeeDTO.setDateOfBirth(birthday);

        EmployeeEM employeeEM = EmployeeMapper.toEmployeeEM(employeeDTO);

        assertEquals(employeeDTO.getId(),
                employeeEM.getId(), "Employee Id should be equal");
        assertEquals(employeeDTO.getLastName(),
                employeeEM.getLastName(), "Employee Last Name should be equal");
        assertEquals(employeeDTO.getAddress(),
                employeeEM.getAddress(), "Employee Address should be equal");
        assertEquals(employeeDTO.getContact(),
                employeeEM.getContact(),"Employee Contact should be equal");
        assertEquals(employeeDTO.getPostalCode(),
                employeeEM.getPostalCode(),"Employee Postal Code should be equal");
        assertEquals(employeeDTO.getDateOfBirth(),
                birthday,"Employee Date of Birth should be equal");
        assertEquals(employeeDTO.getFirstName(),
                employeeEM.getFirstName(),"Employee First Name should be equal");
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
        employeeEM.setBirthday(birthday);

        EmployeePreferencesEM employeePreferencesEM = new EmployeePreferencesEM();
        employeePreferencesEM.setPreferredHours(10.5);
        employeeEM.setPreferences(List.of(employeePreferencesEM));

        SchedEngEmpDTO employee = EmployeeMapper.toEmployeeForScheduleEngine(employeeEM);
        assertEquals(employeeEM.getId(),
                employee.getId(), "Employee Id should be equal");
        assertEquals(employeeEM.getFirstName()+employeeEM.getLastName(),
                employee.getName(), "Employee Last Name should be equal");
        assertEquals(employeeEM.getFirstName()+employeeEM.getLastName(),
                employee.getName(),"Employee First Name should be equal");
        assertEquals(employeeEM.getBirthday(),
                birthday,"Employee Date of Birth should be equal");

        assertEquals(employeePreferencesEM.getPreferredHours(),
                employee.getHoursPreference(),"Employee Hours Preference should be equal");
        
    }


}