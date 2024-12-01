package org.tucno.springboot.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.security.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
