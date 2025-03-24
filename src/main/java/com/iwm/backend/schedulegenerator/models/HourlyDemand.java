package com.iwm.backend.schedulegenerator.models;

import java.util.List;

public class HourlyDemand {

    private String date;

    private int currentHour;

    private List<Integer> demandNext2Hours;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCurrentHour() {
        return currentHour;
    }

    public void setCurrentHour(int currentHour) {
        this.currentHour = currentHour;
    }

    public List<Integer> getDemandNext2Hours() {
        return demandNext2Hours;
    }

    public void setDemandNext2Hours(List<Integer> demandNext2Hours) {
        this.demandNext2Hours = demandNext2Hours;
    }
}
