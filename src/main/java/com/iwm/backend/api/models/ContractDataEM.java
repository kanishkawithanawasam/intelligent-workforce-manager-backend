package com.iwm.backend.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Contract_data")
public class ContractDataEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "hourly_rate")
    private double horlyRate;

    @Column(name = "max_hours")
    private double maxHoursPerWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_contract_employee"))
    @JsonBackReference
    private EmployeeEM employee;
}
