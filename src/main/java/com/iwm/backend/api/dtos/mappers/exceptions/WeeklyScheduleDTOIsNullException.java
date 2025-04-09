package com.iwm.backend.api.dtos.mappers.exceptions;

public class WeeklyScheduleDTOIsNullException extends RuntimeException {
    public WeeklyScheduleDTOIsNullException() {
        super("WeeklyScheduleDTO is null !");
    }
}
