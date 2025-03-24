package com.iwm.backend.schedulegenerator;


import com.iwm.backend.schedulegenerator.models.DaySchedule;
import com.iwm.backend.schedulegenerator.models.HourlyDemand;
import com.iwm.backend.schedulegenerator.models.Shift;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;

import java.util.List;

/**
 * Handles optimisation of the current schedule according to the live demand.
 * @author kanishka withanawasam
 * @version 1.0
 */
public class HourlySchedulesOptimiser {


    private HourlyDemand hourlyDemand;
    private DaySchedule daySchedule;
    private List<Integer[]> dayBinaryRepresentation;

    /**
     *
     * @param hourlyDemand Hourly demand for the next two hours.
     * @param weeklySchedule Schedule of the week.
     */
    public HourlySchedulesOptimiser(HourlyDemand hourlyDemand, WeeklySchedule weeklySchedule) {
        this.hourlyDemand = hourlyDemand;
        daySchedule = new DaySchedule(weeklySchedule, hourlyDemand.getDate());
    }

    private List<Integer[]> getDayBinaryRepresentation() {

        for(Shift shift : daySchedule.getShifts()) {

        }

        return dayBinaryRepresentation;
    }
}
