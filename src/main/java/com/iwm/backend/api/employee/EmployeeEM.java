package com.iwm.backend.api.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iwm.backend.api.contract.ContractDataEM;
import com.iwm.backend.api.models.EmployeePreferencesEM;
import com.iwm.backend.api.shift.ShiftEM;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Employee")
public class EmployeeEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate birthday;

    @Column(name = "address_line1")
    private String address;

    @Column(name = "post_code")
    private String postalCode;

    @Column(name = "contact_number")
    private String contact;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShiftEM> shifts = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ContractDataEM> contractData;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EmployeePreferencesEM> preferences;
}
