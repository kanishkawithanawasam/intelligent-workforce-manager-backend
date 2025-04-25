package com.iwm.backend.modules.preferences;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iwm.backend.modules.employee.EmployeeEM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing employee preferences in the system.
 * This class stores various preferences and settings associated with an employee.
 */
@Getter
@Setter
@Entity
@Table(name = "Preferences")
public class EmployeePreferencesEM {

    /**
     * Unique identifier for the employee preferences.
     * Auto-generated value using identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * The preferred number of weekly working hours for the employee.
     * Stored in the database as 'weekly_hours'.
     */
    @Column(name = "weekly_hours")
    private double preferredHours;

    /**
     * Reference to the employee associated with these preferences.
     * Many preferences can be associated with one employee.
     * Lazy fetching is used for performance optimisation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
    foreignKey = @ForeignKey(name = "fk_preferences_employee"))
    @JsonBackReference(value = "employee-preferences")
    private EmployeeEM employee;
}
