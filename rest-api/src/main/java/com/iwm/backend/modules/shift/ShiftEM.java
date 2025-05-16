package com.iwm.backend.modules.shift;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iwm.backend.modules.employee.EmployeeEM;
import com.iwm.backend.modules.schedules.WeeklyScheduleEM;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Shift")
public class ShiftEM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "shift_date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_shift_employee"))
    @JsonBackReference(value = "employee-shifts")
    @ToString.Exclude
    private EmployeeEM employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_shift_schedule"))
    @JsonBackReference(value = "schedule-shifts")
    @ToString.Exclude
    private WeeklyScheduleEM weeklySchedule;
}
