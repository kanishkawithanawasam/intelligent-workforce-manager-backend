package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftEM;

import java.util.List;
import java.time.temporal.ChronoUnit;

public class DayScheduleUtils {


    public static double calculateCost(List<ShiftEM> shiftEMList) {
        double sum = 0;
        for (ShiftEM shiftEM : shiftEMList) {
            sum+=(ChronoUnit.MINUTES.between(shiftEM.getStartTime(),shiftEM.getEndTime())/60.0);
        }
        return sum;
    }
}
