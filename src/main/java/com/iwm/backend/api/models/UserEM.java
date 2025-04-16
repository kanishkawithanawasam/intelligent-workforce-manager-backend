package com.iwm.backend.api.models;

import com.iwm.backend.api.employee.EmployeeEM;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
public class UserEM {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    private String email;
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private EmployeeEM employee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Access_Roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "access_role_id")
    )
    private Set<AccessRoleEM> roles = new HashSet<>();
}
