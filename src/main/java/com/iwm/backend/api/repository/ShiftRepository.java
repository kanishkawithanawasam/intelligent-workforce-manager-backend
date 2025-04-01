package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.ShiftEM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftEM, Long> {
}
