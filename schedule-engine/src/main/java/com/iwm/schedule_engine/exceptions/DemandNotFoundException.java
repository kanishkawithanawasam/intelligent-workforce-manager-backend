package com.iwm.schedule_engine.exceptions;

public class DemandNotFoundException extends RuntimeException {

    public DemandNotFoundException() {
        super("DemandPreset Not Found! Please check your parameters.");
    }
}
