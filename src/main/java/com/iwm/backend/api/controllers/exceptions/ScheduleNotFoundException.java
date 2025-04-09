package com.iwm.backend.api.controllers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException() {
        super("There is no schedule found for this week!");
    }
}
