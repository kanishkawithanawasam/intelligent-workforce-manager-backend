package com.iwm.backend.schedulegenerator.exceptions;

public class DemandNotFoundException extends RuntimeException {

    public DemandNotFoundException() {
        super("Demand Not Found! Please check your parameters.");
    }
}
