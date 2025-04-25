package com.iwm.backend.modules.preferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePreferencesRepository extends JpaRepository<EmployeePreferencesEM, Long> {

    EmployeePreferencesEM findByEmployeeId(Long id);

}
