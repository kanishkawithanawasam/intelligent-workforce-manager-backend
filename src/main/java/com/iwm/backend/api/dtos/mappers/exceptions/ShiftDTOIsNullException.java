package com.iwm.backend.api.dtos.mappers.exceptions;

public class ShiftDTOIsNullException extends RuntimeException {
    public ShiftDTOIsNullException() {
        super("ShiftGO DTO is null");
    }
}
