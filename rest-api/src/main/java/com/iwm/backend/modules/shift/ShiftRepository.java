package com.iwm.backend.modules.shift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftEM, Long> {

    @Query("SELECT s FROM ShiftEM s WHERE s.employee.id = :empId AND s.weeklySchedule.id = :scheduleId")
    List<ShiftEM> findByWeeklyScheduleAndEmployee(
            @Param("scheduleId") long weeklyScheduleId,@Param("empId") long employeeId);

    List<ShiftEM> findShiftEMByDate(LocalDate date);
}
