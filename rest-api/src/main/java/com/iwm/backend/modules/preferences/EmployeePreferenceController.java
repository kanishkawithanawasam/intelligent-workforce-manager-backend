package com.iwm.backend.modules.preferences;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing employee preferences.
 * Handles HTTP requests related to employee preferences operations.
 */
@RestController
@RequestMapping("/preferences")
public class EmployeePreferenceController{
    
    private final EmployeePreferenceService employeePreferenceService;

    /**
     * Constructs EmployeePreferenceController with required service dependency.
     *
     * @param employeePreferenceService the service for handling employee preferences operations
     */
    public EmployeePreferenceController(EmployeePreferenceService employeePreferenceService) {
        this.employeePreferenceService = employeePreferenceService;
    }


    /**
     * Retrieves preferences for a specific employee.
     *
     * @param employeeId the ID of the employee whose preferences are to be retrieved
     * @return ResponseEntity containing the employee preferences
     */
    @GetMapping()
    public ResponseEntity<?> getPreferencesByEmployeeId(Long employeeId) {
        return ResponseEntity.ok(employeePreferenceService.getEmployeePreferences(employeeId));
    }

    /**
     * Saves or updates employee preferences.
     *
     * @param dto the EmployeePreferenceDTO containing preference data to be saved
     * @return ResponseEntity containing the saved employee preferences
     */
    @PostMapping("/save")
    public ResponseEntity<?> savePreferences(EmployeePreferenceDTO dto) {
        return ResponseEntity.ok(employeePreferenceService.saveEmployeePreferences(dto));
    }
}
