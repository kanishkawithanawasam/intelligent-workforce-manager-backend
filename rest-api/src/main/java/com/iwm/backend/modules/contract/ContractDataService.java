package com.iwm.backend.modules.contract;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ContractDataService {

    private final ContractDataRepository contractDataRepository;

    public ContractDataService(ContractDataRepository contractDataRepository) {
        this.contractDataRepository = contractDataRepository;
    }

    @Transactional
    public ContractDataDTO getLatestContractData(long employeeId) {
        ContractDataEM contractDataEM= contractDataRepository.findLatestContractDataByEmployeeId(employeeId);
        return ContractDataMapper.toContractDataDTO(contractDataEM);
    }


    @Transactional
    public ContractDataDTO saveContractData(ContractDataDTO contractDataDTO) {
        ContractDataEM contractDataEM=ContractDataMapper.toContractDataEM(contractDataDTO);
        contractDataEM= contractDataRepository.save(contractDataEM);
        return ContractDataMapper.toContractDataDTO(contractDataEM);
    }
}
