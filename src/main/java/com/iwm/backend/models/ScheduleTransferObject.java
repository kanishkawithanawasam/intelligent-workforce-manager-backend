package com.iwm.backend.models;

import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.schedulegenerator.models.Shift;

import java.time.LocalDate;
import java.util.*;

public class ScheduleTransferObject {

    private Map<LocalDate, List<Shift>> shifts = new HashMap<>();

    public ScheduleTransferObject(WeeklySchedule weeklySchedule) {
        for (Shift shift : weeklySchedule.getShifts()) {
            if (!shifts.containsKey(shift.getDate())){
                List<Shift> shiftList = new ArrayList<>();
                shiftList.add(shift);
                shifts.put(shift.getDate(),shiftList);
            }else{
                shifts.get(shift.getDate()).add(shift);
            }
        }

    }


    public Map<LocalDate, List<Shift>> getShifts() {
        return shifts;
    }
}
