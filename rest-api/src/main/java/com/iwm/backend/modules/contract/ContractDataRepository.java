package com.iwm.backend.modules.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDataRepository extends JpaRepository<ContractDataEM,Long> {


    ContractDataEM findLatestContractDataByEmployeeId(Long employeeId);
}
