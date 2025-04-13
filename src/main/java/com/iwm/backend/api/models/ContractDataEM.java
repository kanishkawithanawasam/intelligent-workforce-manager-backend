package com.iwm.backend.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Contract_data")
public class ContractDataEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "hourly_rate")
    private double hourlyRate;

    @Column(name = "max_hours_per_week")
    private double maxHoursPerWeek;

    @Column(name = "min_hours_per_week")
    private double minHoursPerWeek;

    @Column
    @Getter
    private String role;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_contract_employee"))
    @JsonBackReference
    private EmployeeEM employee;
}
