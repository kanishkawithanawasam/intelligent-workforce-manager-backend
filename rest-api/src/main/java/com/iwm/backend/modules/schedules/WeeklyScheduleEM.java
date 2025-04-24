package com.iwm.backend.modules.schedules;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iwm.backend.modules.shift.ShiftEM;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a weekly schedule in the system.
 * This class maps to the "Schedule" table in the database and contains information
 * about weekly work schedules including their start dates and associated shifts.
 */
@Data
@Entity
@Table(name = "Schedule")
public class WeeklyScheduleEM {

    /**
     * Unique identifier for the weekly schedule.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Timestamp when this schedule was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createTime;

    /**
     * The start date of the weekly schedule.
     */
    @Column(name = "week_start_date")
    private LocalDate scheduleStartDate;

    /**
     * List of shifts associated with this weekly schedule.
     * This is a bidirectional relationship managed by the weeklySchedule field in ShiftEM.
     */
    @OneToMany(mappedBy = "weeklySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "schedule-shifts")
    private List<ShiftEM> shifts = new ArrayList<>();


    /**
     * Default constructor for WeeklyScheduleEM.
     * Creates a new instance with an empty list of shifts.
     */
    public WeeklyScheduleEM() {

    }
}


