package com.iwm.backend.api.controllers;


import com.iwm.backend.api.dtos.contractdata.ContractDataDTO;
import com.iwm.backend.api.services.ContractDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class ContractDataController {

    private final ContractDataService contractDataService;

    public ContractDataController(ContractDataService contractDataService) {
        this.contractDataService = contractDataService;
    }

    @GetMapping("/by-employee")
    public ResponseEntity<ContractDataDTO> getLatestContractData(@RequestParam long employeeId) {
        return ResponseEntity.ok(contractDataService.getLatestContractData(employeeId));
    }

    @PostMapping
    public ResponseEntity<ContractDataDTO> createContractData(@RequestBody ContractDataDTO contractDataDTO) {
        return ResponseEntity.ok(contractDataService.saveContractData(contractDataDTO));
    }

}
