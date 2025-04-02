package com.iwm.backend.schedulegenerator.configurations;

import com.iwm.backend.schedulegenerator.configurations.businessconfigs.DemandPreset;
import com.iwm.backend.schedulegenerator.configurations.businessconfigs.EmployeeHoursLimits;
import com.iwm.backend.schedulegenerator.configurations.businessconfigs.OpeningHours;

public class BusinessConfigs {
    public OpeningHours opening_hours;
    public EmployeeHoursLimits employee_hours_limits;
    public int minimum_employees_per_shift;
    public DemandPreset demand_preset;
}
