package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.EmployeeEM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEM, Long> {
}
