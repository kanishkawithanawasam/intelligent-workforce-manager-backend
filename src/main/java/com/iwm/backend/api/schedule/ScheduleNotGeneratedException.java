package com.iwm.backend.api.schedule;

public class ScheduleNotGeneratedException extends RuntimeException {
    public ScheduleNotGeneratedException() {

        super("Schedule not generated!");
    }
}
