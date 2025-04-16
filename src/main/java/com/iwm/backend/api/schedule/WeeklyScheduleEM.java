package com.iwm.backend.api.schedule;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iwm.backend.api.shift.ShiftEM;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Schedule")
public class WeeklyScheduleEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "created_at")
    private LocalDateTime createTime;

    @Column(name = "week_start_date")
    private LocalDate scheduleStartDate;

    @OneToMany(mappedBy = "weeklySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShiftEM> shifts = new ArrayList<>();
}


