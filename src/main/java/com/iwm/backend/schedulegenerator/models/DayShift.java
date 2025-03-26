package com.iwm.backend.schedulegenerator.models;

public class DayShift{

    private int[] shiftBinaryRepresentation;
    private Employee employee;

    public DayShift(int[] shiftBinaryRepresentation, Employee employee) {
        this.shiftBinaryRepresentation = shiftBinaryRepresentation;
        this.employee = employee;
    }

    public int[] getShiftBinaryRepresentation() {
        return shiftBinaryRepresentation;
    }

    public Employee getEmployee() {
        return employee;
    }
}
