package com.iwm.backend.modules.schedule_engine.exceptions;

public class InvalidDemandMapException extends RuntimeException {
    public InvalidDemandMapException() {
        super("DemandPreset map is inconsistent! Please check if the values in demand is valid");
    }
}
