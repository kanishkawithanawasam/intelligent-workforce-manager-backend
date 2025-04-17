package com.iwm.backend.modules.schedules;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeeklyScheduleNotFoundException extends RuntimeException {

    public WeeklyScheduleNotFoundException() {
        super("There is no schedule found for this week!");
    }
}
