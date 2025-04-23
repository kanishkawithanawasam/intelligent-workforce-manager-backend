package com.iwm.schedule_engine.configurations;

import com.iwm.schedule_engine.configurations.businessconfigs.DemandPreset;
import com.iwm.schedule_engine.configurations.businessconfigs.EmployeeHoursLimits;
import com.iwm.schedule_engine.configurations.businessconfigs.OpeningHours;

public class BusinessConfigs {
    public OpeningHours opening_hours;
    public EmployeeHoursLimits employee_hours_limits;
    public int minimum_employees_per_shift;
    public DemandPreset demand_preset;
}
