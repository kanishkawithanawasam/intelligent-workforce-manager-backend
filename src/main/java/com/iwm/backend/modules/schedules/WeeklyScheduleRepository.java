package com.iwm.backend.modules.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEM, Long> {

    @Query("SELECT MAX(s.scheduleStartDate) FROM WeeklyScheduleEM s")
    LocalDate findLatestWeekStartDate();

    @Query("SELECT s.scheduleStartDate FROM WeeklyScheduleEM s WHERE s.scheduleStartDate BETWEEN :start AND :end")
    List<LocalDate> findStartDatesBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    WeeklyScheduleEM findByScheduleStartDate(LocalDate scheduleStartDate);
}
