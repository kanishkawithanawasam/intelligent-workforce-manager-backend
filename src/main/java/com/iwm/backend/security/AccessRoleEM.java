package com.iwm.backend.security;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Access_Roles")
@Data
public class AccessRoleEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_role_id")
    private long id;

    private String role;
}
