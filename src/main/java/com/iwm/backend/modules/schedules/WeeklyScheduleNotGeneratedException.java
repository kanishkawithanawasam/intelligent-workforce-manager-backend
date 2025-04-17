package com.iwm.backend.modules.schedules;

public class WeeklyScheduleNotGeneratedException extends RuntimeException {
    public WeeklyScheduleNotGeneratedException() {

        super("Schedule not generated!");
    }
}
