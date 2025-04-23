package com.iwm.backend.modules.employee;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String contact;
    private String postalCode;
}
