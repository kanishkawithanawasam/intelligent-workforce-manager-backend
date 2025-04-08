package com.iwm.backend.api.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeFileDTO {


    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String contact;


    private String currentRole;
    private LocalDate startDate;
    private LocalDate endDate;
    private String employmentType;
    private double hourlyRate;
    private double contractedHours;
    private double maxHoursPerWeek;


    private String emergencyContactName;
    private String emergencyContactPhone;
}
