package com.iwm.backend.modules.employee;

import lombok.Data;

/**
 * Data Transfer Object representing basic employee information.
 * Used for lightweight data transfer in list operations and basic employee information display.
 * Contains only essential identification fields extracted from {@link EmployeeEM}.
 *
 * @see EmployeeEM
 * @see EmployeeDTO
 * @see EmployeeMapper
 */
@Data
public class EmployeeBasicInfoDTO {

    /**
     * The unique identifier of the employee.
     * Maps to the primary key in the Employee table.
     */
    private long id;

    /**
     * The first name (given name) of the employee.
     * Maps to first_name column in the Employee table.
     */
    private String firstName;

    /**
     * The last name (surname) of the employee.
     * Maps to last_name column in the Employee table.
     */
    private String lastName;

}
