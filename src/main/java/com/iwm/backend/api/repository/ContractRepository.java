package com.iwm.backend.api.repository;

import com.iwm.backend.api.models.ContractDataEM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractDataEM,Long> {
}
