package com.iwm.backend.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEM, Long> {

    UserEM findByEmail(String email);
    boolean existsByEmail(String email);
}
