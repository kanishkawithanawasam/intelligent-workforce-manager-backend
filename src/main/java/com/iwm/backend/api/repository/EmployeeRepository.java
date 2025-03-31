package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.EmployeeEM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEM, Long> {
}
