package com.iwm.backend.schedulegenerator.exceptions;

public class InvalidDemandMapException extends RuntimeException {
    public InvalidDemandMapException() {
        super("Demand map is inconsistent! Please check if the values in demand is valid");
    }
}
