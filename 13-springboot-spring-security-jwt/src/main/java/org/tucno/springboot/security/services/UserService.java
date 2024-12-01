package org.tucno.springboot.security.services;

import org.tucno.springboot.security.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
}
