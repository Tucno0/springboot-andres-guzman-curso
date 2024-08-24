package org.tucno.springboot.error.services;

import org.tucno.springboot.error.models.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
}
