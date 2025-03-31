package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.ShiftEM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<ShiftEM, Long> {
}
