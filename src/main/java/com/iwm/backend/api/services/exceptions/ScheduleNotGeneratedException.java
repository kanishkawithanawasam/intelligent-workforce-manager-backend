package com.iwm.backend.api.services.exceptions;

public class ScheduleNotGeneratedException extends RuntimeException {
    public ScheduleNotGeneratedException() {

        super("Schedule not generated!");
    }
}
