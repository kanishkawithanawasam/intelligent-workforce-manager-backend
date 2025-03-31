package com.iwm.backend.api.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
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
    private List<ShiftEM> shifts = new ArrayList<>();
}
