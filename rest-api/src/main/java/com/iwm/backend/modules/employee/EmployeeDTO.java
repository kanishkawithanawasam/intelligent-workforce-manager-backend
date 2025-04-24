package com.iwm.backend.modules.employee;


import com.iwm.schedule_engine.models.mappers.EmployeeMapper;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object representing employee demographic information.
 * Used for transferring employee data between the service layer and REST API endpoints.
 * This class is mapped from and to {@link EmployeeEM} entity using {@link EmployeeMapper}.
 */
@Data
public class EmployeeDTO {
    /**
     * Unique identifier of the employee
     */
    private long id;

    /**
     * First name of the employee
     */
    private String firstName;

    /**
     * Last name of the employee
     */
    private String lastName;

    /**
     * Employee's date of birth
     */
    private LocalDate dateOfBirth;

    /**
     * Employee's residential address
     */
    private String address;

    /**
     * Employee's contact information (phone number or email)
     */
    private String contact;

    /**
     * Postal code of the employee's address
     */
    private String postalCode;
}
