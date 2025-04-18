package com.iwm.backend.security;

import com.iwm.backend.modules.employee.EmployeeEM;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "User")
@Data
public class UserEM {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private EmployeeEM employee;
}
