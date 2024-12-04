package org.tucno.springboot.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.security.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
