package org.tucno.springboot.error;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tucno.springboot.error.models.domain.User;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public List<User> users() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "Doe"));
        users.add(new User(2L, "Jane", "Doe"));
        users.add(new User(3L, "Pepe", "Pecas"));
        users.add(new User(4L, "Fulanito", "De Tal"));
        users.add(new User(5L, "El", "Chapucero"));

        return users;
    }
}
