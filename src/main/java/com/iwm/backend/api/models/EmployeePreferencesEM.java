package com.iwm.backend.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iwm.backend.api.employee.EmployeeEM;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Preferences")
public class EmployeePreferencesEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "weekly_hours")
    private double preferredHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
    foreignKey = @ForeignKey(name = "fk_preferences_employee"))
    @JsonBackReference
    private EmployeeEM employee;
}
