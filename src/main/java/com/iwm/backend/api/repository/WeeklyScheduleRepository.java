package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.WeeklyScheduleEM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEM, Long> {
}
