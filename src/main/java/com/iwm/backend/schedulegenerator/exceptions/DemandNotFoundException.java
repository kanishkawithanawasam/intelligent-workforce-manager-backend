package com.iwm.backend.schedulegenerator.exceptions;

public class DemandNotFoundException extends RuntimeException {

    public DemandNotFoundException() {
        super("DemandPreset Not Found! Please check your parameters.");
    }
}
