package com.iwm.schedule_engine.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyScheduleTest {



    @Test
    void TestThatAnEmployeeIsNotAssingedTwoShiftsOnTheSameDay() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        // Defines a test shift
        Shift shift = new Shift();
        shift.setDate(LocalDate.of(2020, 1, 1));
        shift.setStartTimeInMinutes(9*60);
        shift.setEndTimeInMinutes(13*60);

        // Defines a test employee
        Employee employee = new Employee();
    }
}