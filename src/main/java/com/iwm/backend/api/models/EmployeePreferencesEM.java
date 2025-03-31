package com.iwm.backend.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Preferences")
public class EmployeePreferencesEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "weekly_hours")
    private double preferredHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
    foreignKey = @ForeignKey(name = "fk_preferences_employee"))
    private EmployeeEM employee;
}
