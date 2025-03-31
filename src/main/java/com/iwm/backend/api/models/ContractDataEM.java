package com.iwm.backend.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "contract_data")
public class ContractDataEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "hourly_rate")
    private double horlyRate;

    @Column(name = "max_hours")
    private double maxHoursPerWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_contract_employee"))
    private EmployeeEM employees;
}
