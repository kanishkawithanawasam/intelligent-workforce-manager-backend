package com.iwm.backend.schedulegenerator.exceptions;

public class InvalidDemandMapException extends RuntimeException {
    public InvalidDemandMapException() {
        super("DemandPreset map is inconsistent! Please check if the values in demand is valid");
    }
}
