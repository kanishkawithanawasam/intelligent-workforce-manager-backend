package com.iwm.backend.modules.employee;


import com.iwm.schedule_engine.models.mappers.EmployeeMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Data Transfer Object representing employee demographic information.
 * Used for transferring employee data between the service layer and REST API endpoints.
 * This class is mapped from {@link EmployeeEM} using {@link EmployeeMapper}.
 */
public record EmployeeDto (
        Long id,                                   // use Long so it can be null on create
        @NotBlank @Size(max = 50) String firstName,
        @NotBlank @Size(max = 50) String lastName,
        @Past @NotNull LocalDate dateOfBirth,
        @Size(max = 255) String address,
        @Size(max = 100) String contact,
        @Size(max = 20) String postalCode
) {}