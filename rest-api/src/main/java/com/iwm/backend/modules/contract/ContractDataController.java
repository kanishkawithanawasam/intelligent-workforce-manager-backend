package com.iwm.backend.modules.contract;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for managing contract data operations.
 * Provides endpoints for retrieving and creating contract data.
 */
@RestController
@RequestMapping("/contracts")
public class ContractDataController {

    private final ContractDataService contractDataService;

    /**
     * Constructs a new ContractDataController with the specified service.
     *
     * @param contractDataService the service for handling contract data operations
     */
    public ContractDataController(ContractDataService contractDataService) {
        this.contractDataService = contractDataService;
    }

    /**
     * Retrieves the latest contract data for a specific employee.
     *
     * @param employeeId the ID of the employee
     * @return ResponseEntity containing the latest ContractDataDTO
     */
    @GetMapping("/by-employee")
    public ResponseEntity<ContractDataDTO> getLatestContractData(@RequestParam long employeeId) {
        return ResponseEntity.ok(contractDataService.getLatestContractData(employeeId));
    }

    /**
     * Creates new contract data.
     *
     * @param contractDataDTO the contract data to create
     * @return ResponseEntity containing the created ContractDataDTO
     */
    @PostMapping
    public ResponseEntity<ContractDataDTO> createContractData(@RequestBody ContractDataDTO contractDataDTO) {
        return ResponseEntity.ok(contractDataService.saveContractData(contractDataDTO));
    }

}
