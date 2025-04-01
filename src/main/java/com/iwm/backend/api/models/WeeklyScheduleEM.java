package com.iwm.backend.api.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

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

    @OneToMany(mappedBy = "weeklySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShiftEM> shifts = new ArrayList<>();
}


