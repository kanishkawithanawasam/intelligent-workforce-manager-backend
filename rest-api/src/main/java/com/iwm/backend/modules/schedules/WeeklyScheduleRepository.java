package com.iwm.backend.modules.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing WeeklySchedule entities.
 * Provides methods for querying and managing weekly schedule data in the database.
 */
@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEM, Long> {

    /**
     * Finds the most recent schedule start date in the database.
     *
     * @return the latest schedule start date, or null if no schedules exist
     */
    @Query("SELECT MAX(s.scheduleStartDate) FROM WeeklyScheduleEM s")
    LocalDate findLatestWeekStartDate();

    /**
     * Finds all schedule start dates that fall within the specified date range.
     *
     * @param start the start date of the range (inclusive)
     * @param end   the end date of the range (inclusive)
     * @return a list of schedule start dates within the specified range
     */
    @Query("SELECT s.scheduleStartDate FROM WeeklyScheduleEM s WHERE s.scheduleStartDate BETWEEN :start AND :end")
    List<LocalDate> findStartDatesBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    /**
     * Finds a weekly schedule by its start date.
     *
     * @param scheduleStartDate the start date of the schedule to find
     * @return the weekly schedule with the specified start date, or null if not found
     */
    WeeklyScheduleEM findByScheduleStartDate(LocalDate scheduleStartDate);
}
