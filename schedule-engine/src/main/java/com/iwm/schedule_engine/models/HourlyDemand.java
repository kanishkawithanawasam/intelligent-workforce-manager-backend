package com.iwm.schedule_engine.models;

import com.iwm.schedule_engine.exceptions.DemandNotFoundException;
import lombok.Getter;

import java.time.LocalDate;
import java.util.TreeMap;


/**
 * Represents the hourly demand forecast during a given period
 */
public class HourlyDemand {

    @Getter
    private final LocalDate date; // Date of the demand forecast

    @Getter
    private final int startTimeInMinutes; // Start of the period

    @Getter
    private final int endTimeInMinutes; // End of the period

    private final TreeMap<Integer,Integer> hourlyDemand; // Demand map for the given period

    public HourlyDemand(LocalDate date,TreeMap<Integer,Integer> hourlyDemand) {
        this.date = date;
        this.hourlyDemand = hourlyDemand;

        if(hourlyDemand.size()<2) {
            throw new DemandNotFoundException();
        }

        // Converts hours into minutes
        startTimeInMinutes = hourlyDemand.firstKey()*60;
        endTimeInMinutes = (hourlyDemand.lastKey()+1)*60;
    }


    public TreeMap<Integer,Integer> getHourlyDemandMap() {
        return hourlyDemand;
    }
}
