package com.iwm.backend.schedulegenerator.models;

import java.time.LocalDate;
import java.util.Map;

public class HourlyDemand {

    private LocalDate date;

    private int startTimeInMinutes;

    private int endTimeInMinutes;

    private Map<Integer,Integer> hourlyDemand;

    public int getStartTimeInMinutes() {
        return startTimeInMinutes;
    }

    public void setStartTime(int startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }

    public int getEndTimeInMinutes() {
        return endTimeInMinutes;
    }

    public void setEndTime(int endTimeInMinutes) {
        this.endTimeInMinutes = endTimeInMinutes;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
