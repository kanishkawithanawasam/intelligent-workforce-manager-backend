package com.iwm.backend.modules.contract;

import com.iwm.backend.modules.employee.EmployeeEM;

/**
 * Mapper class responsible for converting between ContractDataEM (Entity Model)
 * and ContractDataDTO (Data Transfer Object) objects.
 */
public class ContractDataMapper {


    /**
     * Converts a ContractDataEM entity to a ContractDataDTO.
     *
     * @param contractDataEM the contract data entity model to convert
     * @return a ContractDataDTO containing the mapped contract data
     */
    public static ContractDataDTO toContractDataDTO(ContractDataEM contractDataEM) {
        ContractDataDTO dto=new ContractDataDTO();
        dto.setContractId(contractDataEM.getId());
        dto.setRole(contractDataEM.getRole());
        dto.setStartDate(contractDataEM.getStartDate());
        dto.setEndDate(contractDataEM.getEndDate());
        dto.setHourlyRate(contractDataEM.getHourlyRate());
        dto.setMaxHoursPerWeek(contractDataEM.getMaxHoursPerWeek());
        dto.setMinHoursPerWeek(contractDataEM.getMinHoursPerWeek());
        dto.setEmployeeId(contractDataEM.getEmployee().getId());
        dto.setEmployeeName(contractDataEM.getEmployee().getFirstName() + " " +
                contractDataEM.getEmployee().getLastName());
        return dto;
    }

    /**
     * Converts a ContractDataDTO to a ContractDataEM entity.
     *
     * @param dto the contract data transfer object to convert
     * @return a ContractDataEM containing the mapped contract data
     */
    public static ContractDataEM toContractDataEM(ContractDataDTO dto) {
        ContractDataEM contractDataEM=new ContractDataEM();
        contractDataEM.setId(dto.getContractId());
        contractDataEM.setRole(dto.getRole());
        contractDataEM.setStartDate(dto.getStartDate());
        contractDataEM.setEndDate(dto.getEndDate());
        contractDataEM.setHourlyRate(dto.getHourlyRate());
        contractDataEM.setMaxHoursPerWeek(dto.getMaxHoursPerWeek());
        contractDataEM.setMinHoursPerWeek(dto.getMinHoursPerWeek());

        EmployeeEM employeeEM=new EmployeeEM();
        employeeEM.setId(dto.getEmployeeId());
        contractDataEM.setEmployee(employeeEM);
        return contractDataEM;
    }
}



