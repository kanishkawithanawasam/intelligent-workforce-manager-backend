package com.iwm.backend.modules.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing ContractData entities.
 * Provides CRUD operations and custom queries for contract-related data.
 */
@Repository
public interface ContractDataRepository extends JpaRepository<ContractDataEM, Long> {

    /**
     * Retrieves the most recent contract data for a specific employee.
     *
     * @param employeeId The unique identifier of the employee
     * @return The most recent ContractDataEM entity for the specified employee
     */
    ContractDataEM findLatestContractDataByEmployeeId(Long employeeId);
}
