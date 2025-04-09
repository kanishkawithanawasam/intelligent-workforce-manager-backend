package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.WeeklyScheduleEM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEM, Long> {
    WeeklyScheduleEM findTopByOrderByCreateTimeDesc();
}
