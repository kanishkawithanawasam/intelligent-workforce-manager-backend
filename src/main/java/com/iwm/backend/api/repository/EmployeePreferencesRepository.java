package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.EmployeePreferencesEM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePreferencesRepository extends JpaRepository<EmployeePreferencesEM, Long> {
}
