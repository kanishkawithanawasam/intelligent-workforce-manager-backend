package com.iwm.backend.api.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Shift implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int startTimeInMinutes;
    private int endTimeInMinutes;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "weekly_schedule_id", nullable = false)
    private WeeklySchedule weeklySchedule;


    private LocalDate date;


    public Shift(LocalDate date, int startTimeInMinutes, int endTimeInMinutes,
                 Employee employee) {
        this.date = date;
        this.endTimeInMinutes = endTimeInMinutes;
        this.startTimeInMinutes = startTimeInMinutes;
        this.employee = employee;
    }

    public Shift() {}

    public LocalDate getDate() {
        return date;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }


    public int getStartTimeInMinutes() {
        return startTimeInMinutes;
    }

    public void setStartTimeInMinutes(int startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }

    public int getEndTimeInMinutes() {
        return endTimeInMinutes;
    }

    public void setEndTimeInMinutes(int endTimeInMinutes) {
        this.endTimeInMinutes = endTimeInMinutes;
    }

    public int getShiftLengthInMinutes() {
        return endTimeInMinutes - startTimeInMinutes;
    }

    @Override
    public String toString() {
        return String.format("Name: %s |  Date: %s | Start-time: %f | End-time: %f",
                employee.getName(), date, startTimeInMinutes/60.0, endTimeInMinutes/60.0);
    }


    @Override
    public Shift clone() {
        try {
            return (Shift) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
