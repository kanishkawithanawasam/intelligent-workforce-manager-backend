package com.iwm.backend.api.services;


import com.iwm.backend.api.dtos.contractdata.ContractDataDTO;
import com.iwm.backend.api.dtos.mappers.ContractDataMapper;
import com.iwm.backend.api.models.ContractDataEM;
import com.iwm.backend.api.repository.ContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ContractDataService {

    private final ContractRepository contractRepository;

    public ContractDataService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional
    public ContractDataDTO getLatestContractData(long employeeId) {
        ContractDataEM contractDataEM=contractRepository.findLatestContractDataByEmployeeId(employeeId);
        return ContractDataMapper.toContractDataDTO(contractDataEM);
    }


    @Transactional
    public ContractDataDTO saveContractData(ContractDataDTO contractDataDTO) {
        ContractDataEM contractDataEM=ContractDataMapper.toContractDataEM(contractDataDTO);
        contractDataEM=contractRepository.save(contractDataEM);
        return ContractDataMapper.toContractDataDTO(contractDataEM);
    }
}
