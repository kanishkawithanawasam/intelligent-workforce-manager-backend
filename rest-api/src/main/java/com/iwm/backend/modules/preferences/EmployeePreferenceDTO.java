package com.iwm.backend.modules.preferences;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePreferenceDTO {
    private long employeeId;
    private long preferenceId;
    private double preferredHours;
}
