package com.iwm.schedule_engine.models.dtos.interfaces;

public interface IScheduleEngineEmpDTO {

    long getId();
    void setId(long id);

    String getName();
    void setName(String name);

    String getRole();
    void setRole(String role);

    double getHoursPreference();
    void setHoursPreference(double hoursPreference);

    double getMaxHoursPerWeek();
    void setMaxHoursPerWeek(double maxHoursPerWeek);

    double getCost();
    void setCost(double cost);
}
