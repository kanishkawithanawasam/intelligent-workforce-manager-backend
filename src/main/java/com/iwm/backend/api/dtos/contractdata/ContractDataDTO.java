package com.iwm.backend.api.dtos.contractdata;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractDataDTO {
    private long contractId;
    private double hourlyRate;
    private double maxHoursPerWeek;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractType;

}
