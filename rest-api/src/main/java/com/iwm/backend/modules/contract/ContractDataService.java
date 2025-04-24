package com.iwm.backend.modules.contract;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


/**
 * Service class responsible for managing contract data operations.
 * Provides functionality to retrieve and save contract data for employees.
 */
@Service
public class ContractDataService {

    private final ContractDataRepository contractDataRepository;

    /**
     * Constructs a new ContractDataService with the required repository.
     *
     * @param contractDataRepository the repository for contract data operations
     */
    public ContractDataService(ContractDataRepository contractDataRepository) {
        this.contractDataRepository = contractDataRepository;
    }

    /**
     * Retrieves the most recent contract data for a specific employee.
     *
     * @param employeeId the ID of the employee
     * @return the latest contract data as DTO
     */
    @Transactional
    public ContractDataDTO getLatestContractData(long employeeId) {
        ContractDataEM contractDataEM= contractDataRepository.findLatestContractDataByEmployeeId(employeeId);
        return ContractDataMapper.toContractDataDTO(contractDataEM);
    }


    /**
     * Saves new contract data to the database.
     *
     * @param contractDataDTO the contract data to be saved
     * @return the saved contract data as DTO
     */
    @Transactional
    public ContractDataDTO saveContractData(ContractDataDTO contractDataDTO) {
        ContractDataEM contractDataEM=ContractDataMapper.toContractDataEM(contractDataDTO);
        contractDataEM= contractDataRepository.save(contractDataEM);
        return ContractDataMapper.toContractDataDTO(contractDataEM);
    }
}
