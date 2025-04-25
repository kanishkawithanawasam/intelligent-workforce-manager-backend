package com.iwm.backend.modules.contract;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iwm.backend.modules.employee.EmployeeEM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity class representing contract data for employees.
 * Maps to the Contract_data table in the database.
 */
@Getter
@Setter
@Entity
@Table(name = "Contract_data")
public class ContractDataEM {

    /**
     * Unique identifier for the contract
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Hourly rate specified in the contract
     */
    @Column(name = "hourly_rate")
    private double hourlyRate;

    /**
     * Maximum working hours per week allowed by the contract
     */
    @Column(name = "max_hours_per_week")
    private double maxHoursPerWeek;

    /**
     * Minimum working hours per week required by the contract
     */
    @Column(name = "min_hours_per_week")
    private double minHoursPerWeek;

    /**
     * Employee's role or position specified in the contract
     */
    @Column
    @Getter
    private String role;

    /**
     * Date when the contract becomes effective
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * Date when the contract expires
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Employee associated with this contract
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_contract_employee"))
    @JsonManagedReference(value = "employee-contract")
    private EmployeeEM employee;
}
