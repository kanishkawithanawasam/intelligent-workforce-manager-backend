package com.iwm.backend.modules.contract;

import com.iwm.backend.modules.employee.EmployeeEM;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Contract Data Mapper Test")
class ContractDataMapperTest {

    @Test
    @SuppressWarnings("Test ContractDataEM to ContractDataDTO")
    void toContractDataDTO() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        
        // Creates employee
        EmployeeEM employeeEM = new EmployeeEM();
        employeeEM.setId(1L);
        employeeEM.setFirstName("John");
        employeeEM.setLastName("Doe");

        // Creates contract
        ContractDataEM contractDataEM = new ContractDataEM();
        contractDataEM.setId(1L);
        contractDataEM.setRole("Developer");
        contractDataEM.setStartDate(startDate);
        contractDataEM.setEndDate(endDate);
        contractDataEM.setHourlyRate(50.0);
        contractDataEM.setMaxHoursPerWeek(40);
        contractDataEM.setMinHoursPerWeek(20);
        contractDataEM.setEmployee(employeeEM);

        // Map and verify
        ContractDataDTO result = ContractDataMapper.toContractDataDTO(contractDataEM);

        assertNotNull(result);
        assertEquals(1L, result.getContractId(), "Contract Id should be equal");
        assertEquals("Developer", result.getRole(), "Contract Role should be equal");
        assertEquals(startDate, result.getStartDate(), "Contract Start Date should be equal");
        assertEquals(endDate, result.getEndDate(), "Contract End Date should be equal");
        assertEquals(50.0, result.getHourlyRate(), "Contract Hourly Rate should be equal");
        assertEquals(40, result.getMaxHoursPerWeek(), "Contract Max Hours Per Week should be equal");
        assertEquals(20, result.getMinHoursPerWeek(), "Contract Min Hours Per Week should be equal");
        assertEquals(1L, result.getEmployeeId(), "Contract Employee Id should be equal");
        assertEquals("John Doe", result.getEmployeeName(), "Contract Employee Name should be equal");
    }

    @Test
    @SuppressWarnings("Test ContractDataDTO to ContractDataEM")
    void toContractDataEM() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        // Creates DTO
        ContractDataDTO contractDataDTO = new ContractDataDTO();
        contractDataDTO.setContractId(1L);
        contractDataDTO.setRole("Developer");
        contractDataDTO.setStartDate(startDate);
        contractDataDTO.setEndDate(endDate);
        contractDataDTO.setHourlyRate(50.0);
        contractDataDTO.setMaxHoursPerWeek(40);
        contractDataDTO.setMinHoursPerWeek(20);
        contractDataDTO.setEmployeeId(1L);
        contractDataDTO.setEmployeeName("John Doe");

        // Map and verify
        ContractDataEM result = ContractDataMapper.toContractDataEM(contractDataDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId(), "Contract Id should be equal");
        assertEquals("Developer", result.getRole(), "Contract Role should be equal");
        assertEquals(startDate, result.getStartDate(), "Contract Start Date should be equal");
        assertEquals(endDate, result.getEndDate(), "Contract End Date should be equal");
        assertEquals(50.0, result.getHourlyRate(), "Contract Hourly Rate should be equal");
        assertEquals(40, result.getMaxHoursPerWeek(), "Contract Max Hours Per Week should be equal");
        assertEquals(20, result.getMinHoursPerWeek(), "Contract Min Hours Per Week should be equal");
        assertEquals(1L, result.getEmployee().getId(), "Contract Employee Id should be equal");
    }


}