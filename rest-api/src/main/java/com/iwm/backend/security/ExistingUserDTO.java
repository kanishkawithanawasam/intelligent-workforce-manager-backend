package com.iwm.backend.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExistingUserDTO {

    private String email;
    private String password;
}
