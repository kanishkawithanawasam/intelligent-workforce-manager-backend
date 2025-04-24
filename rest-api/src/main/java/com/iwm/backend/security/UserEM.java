package com.iwm.backend.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iwm.backend.modules.employee.EmployeeEM;
import jakarta.persistence.*;
import lombok.Data;


/**
 * User entity for Spring Security.
 * @version 1.0
 */
@Entity
@Table(name = "User")
@Data
public class UserEM {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * User email acts as username.
     */
    @Column(unique = true)
    private String email;

    /**
     * User password
     */
    private String password;

    /**
     * Association to Employee
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id",unique = true)
    @JsonBackReference(value = "employee-user")
    private EmployeeEM employee;
}
