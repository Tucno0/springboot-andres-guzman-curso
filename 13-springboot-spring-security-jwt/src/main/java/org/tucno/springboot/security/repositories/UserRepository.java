package org.tucno.springboot.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.security.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
