package com.iwm.backend.security;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  NewUserDTO{

    private String email;
    private String password;
    private String role;
    private long employeeId;
}
