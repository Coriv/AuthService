package com.microservices.authservice.auth;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AuthDao extends CrudRepository<Authentication, Long> {

    Optional<Authentication> findByUsername(String username);
    Optional<Authentication> findByUserId(Long id);
}
