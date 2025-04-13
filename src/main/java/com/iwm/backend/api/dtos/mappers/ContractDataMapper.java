package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.dtos.contractdata.ContractDataDTO;
import com.iwm.backend.api.models.ContractDataEM;

public class ContractDataMapper {


    public static ContractDataDTO toContractDataDTO(ContractDataEM contractDataEM) {
        ContractDataDTO dto=new ContractDataDTO();
        dto.setContractId(contractDataEM.getId());
        dto.setRole(contractDataEM.getRole());
        dto.setStartDate(contractDataEM.getStartDate());
        dto.setEndDate(contractDataEM.getEndDate());
        dto.setHourlyRate(contractDataEM.getHourlyRate());
        return dto;
    }
}
