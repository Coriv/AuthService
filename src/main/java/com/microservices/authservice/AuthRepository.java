package com.microservices.authservice;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AuthRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
