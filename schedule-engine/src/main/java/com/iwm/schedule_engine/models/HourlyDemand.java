package com.iwm.schedule_engine.models;

import com.iwm.schedule_engine.exceptions.DemandNotFoundException;

import java.time.LocalDate;
import java.util.TreeMap;

public class HourlyDemand {

    private final LocalDate date;

    private final int startTimeInMinutes;

    private final int endTimeInMinutes;

    private final TreeMap<Integer,Integer> hourlyDemand;

    public HourlyDemand(LocalDate date,TreeMap<Integer,Integer> hourlyDemand) {
        this.date = date;
        this.hourlyDemand = hourlyDemand;

        if(hourlyDemand.size()<2) {
            throw new DemandNotFoundException();
        }

        // Convert hours into minutes
        startTimeInMinutes = hourlyDemand.firstKey()*60;
        endTimeInMinutes = (hourlyDemand.lastKey()+1)*60;
    }


    public int getStartTimeInMinutes() {
        return startTimeInMinutes;
    }


    public int getEndTimeInMinutes() {
        return endTimeInMinutes;
    }

    public LocalDate getDate() {
        return date;
    }

    public TreeMap<Integer,Integer> getHourlyDemandMap() {
        return hourlyDemand;
    }
}
