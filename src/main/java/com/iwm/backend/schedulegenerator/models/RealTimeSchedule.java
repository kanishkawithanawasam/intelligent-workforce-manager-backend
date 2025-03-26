package com.iwm.backend.schedulegenerator.models;

import java.util.ArrayList;
import java.util.List;

public class RealTimeSchedule {

    private List<Shift> shifts = new ArrayList<>();

    public List<Shift> getShifts() {
        return shifts;
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }
}
