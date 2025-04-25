package com.iwm.backend.modules.preferences;

import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing employee preferences operations.
 * Provides methods to retrieve and save employee preferences in the system.
 * This service acts as an intermediary between the controller layer and the data access layer,
 * handling the business logic for employee preferences management.
 */
@Service
public class EmployeePreferenceService {

    /**
     * Repository for accessing and managing employee preferences data in the database.
     */
    private EmployeePreferencesRepository employeePreferencesRepository;

    /**
     * Constructs a new EmployeePreferenceService with the required repository.
     *
     * @param employeePreferencesRepository Repository for employee preferences data access
     */
    public EmployeePreferenceService(EmployeePreferencesRepository employeePreferencesRepository) {
        this.employeePreferencesRepository = employeePreferencesRepository;
    }

    /**
     * Retrieves employee preferences for a specific employee.
     *
     * @param employeeId The ID of the employee whose preferences are to be retrieved
     * @return EmployeePreferencesEM containing the employee's preferences
     */
    public EmployeePreferenceDTO getEmployeePreferences(Long employeeId) {
        return PreferenceMapper.toDTO(employeePreferencesRepository.findByEmployeeId(employeeId));
    }

    /**
     * Saves or updates employee preferences in the database.
     * Converts the provided DTO to an entity model, saves it, and returns the result as a DTO.
     *
     * @param dto The employee preferences data transfer object containing the preferences to be saved
     * @return EmployeePreferenceDTO The saved employee preferences converted to DTO
     */
    public EmployeePreferenceDTO saveEmployeePreferences(EmployeePreferenceDTO dto) {
        
        EmployeePreferencesEM em = employeePreferencesRepository.save(PreferenceMapper.toEM(dto));
        return PreferenceMapper.toDTO(em);
    }
    
}
