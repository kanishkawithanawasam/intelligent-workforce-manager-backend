package com.iwm.backend.modules.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iwm.backend.modules.contract.ContractDataEM;
import com.iwm.backend.modules.preferences.EmployeePreferencesEM;
import com.iwm.backend.modules.shift.ShiftEM;
import com.iwm.backend.security.UserEM;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an employee in the system.
 * Contains personal information and relationships with other entities like shifts, contracts and preferences.
 */
@Data
@Entity
@Table(name = "Employee")
@ToString(exclude = {"preferences"})
public class EmployeeEM {

    /**
     * Unique identifier for the employee
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Employee's first name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Employee's last name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Employee's date of birth
     */
    @Column(name = "date_of_birth")
    private LocalDate birthday;

    /**
     * Employee's street address
     */
    @Column(name = "address_line1")
    private String address;

    /**
     * Employee's postal code
     */
    @Column(name = "post_code")
    private String postalCode;

    /**
     * Employee's contact phone number
     */
    @Column(name = "contact_number")
    private String contact;

    /**
     * List of shifts assigned to this employee
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "employee-shifts")
    private List<ShiftEM> shifts = new ArrayList<>();

    /**
     * Employee's contract history
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "employee-contract")
    private List<ContractDataEM> contractData;

    /**
     * Employee's work preferences
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonBackReference(value = "employee-preferences")
    private List<EmployeePreferencesEM> preferences;

    /**
     * Associated user account
     */
    @OneToOne(mappedBy = "employee")
    @JsonManagedReference(value = "employee-user")
    private UserEM user;
}
