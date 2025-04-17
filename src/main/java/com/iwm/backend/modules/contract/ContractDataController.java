package com.iwm.backend.modules.contract;


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
