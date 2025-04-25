package com.iwm.backend.modules.contract;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for Contract information.
 * Used to transfer contract-related data between different frontend and controller.
 */
@Data
public class ContractDataDTO {
    /**
     * Unique identifier of the contract
     */
    private long contractId;

    /**
     * Hourly rate of pay for the contract
     */
    private double hourlyRate;

    /**
     * Maximum allowed working hours per week
     */
    private double maxHoursPerWeek;

    /**
     * Date when the contract becomes effective
     */
    private LocalDate startDate;

    /**
     * Date when the contract expires
     */
    private LocalDate endDate;

    /**
     * Employee's role or position in the contract
     */
    private String role;

    /**
     * Minimum required working hours per week
     */
    private double minHoursPerWeek;

    /**
     * Full name of the employee
     */
    private String employeeName;

    /**
     * Unique identifier of the employee
     */
    private long employeeId;
}
