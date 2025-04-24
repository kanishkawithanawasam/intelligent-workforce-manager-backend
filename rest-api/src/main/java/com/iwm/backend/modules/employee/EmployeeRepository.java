package com.iwm.backend.modules.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for handling employee-related database operations.
 * Provides CRUD operations for {@link EmployeeEM} entities using Spring Data JPA.
 * This repository manages employee data persistence and retrieval operations.
 *
 * @see JpaRepository
 * @see EmployeeEM
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEM, Long> {
}
