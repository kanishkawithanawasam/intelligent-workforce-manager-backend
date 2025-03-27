package com.iwm.backend.schedulegenerator.models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HourlyDemand {

    private LocalDate date;

    private int startTimeInMinutes;

    private int endTimeInMinutes;

    private final Map<Integer,Integer> hourlyDemand = new HashMap<>();

    public int getStartTimeInMinutes() {
        return startTimeInMinutes;
    }

    public void setStartTimeInMinutes(int startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }

    public int getEndTimeInMinutes() {
        return endTimeInMinutes;
    }

    public void setEndTimeInMinutes(int endTimeInMinutes) {
        this.endTimeInMinutes = endTimeInMinutes;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Integer,Integer> getHourlyDemandMap() {
        return hourlyDemand;
    }
}
