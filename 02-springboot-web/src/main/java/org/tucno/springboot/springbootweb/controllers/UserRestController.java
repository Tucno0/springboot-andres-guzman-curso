package org.tucno.springboot.springbootweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tucno.springboot.springbootweb.models.User;
import org.tucno.springboot.springbootweb.models.dtos.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// La anotación @RestController indica que la clase es un controlador de Spring MVC y que manejará las peticiones HTTP
// Solo se debe usar en clases que manejen las respuestas JSON de la aplicación web
@RestController

// Se puede usar la anotación @Controller en lugar de @RestController para manejar las respuestas JSON
//@Controller

// La anotación @RequestMapping indica la URL base que se usará para acceder a los métodos de la clase
@RequestMapping("/api")
public class UserRestController {

    // Si se usa la anotación @Controller, se debe usar la anotación @GetMapping en lugar de @RequestMapping
    // El RequestMethod.GET indica que el método solo responderá a peticiones HTTP GET
//    @RequestMapping(params = "/details", method = RequestMethod.GET)
    @GetMapping("/details-map")
    public Map<String, Object> detailsMap() {
        User user = new User("Jhampier", "Tucno");
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Hola mundo Spring boot");
        response.put("user", user);

        return response;
    }

    @GetMapping("/details")
    public UserDto details() {
        UserDto userDto = new UserDto();
        User user = new User("Jhampier", "Tucno");
        userDto.setTitle("Hola mundo Spring boot");
        userDto.setUser(user);

        return userDto;
    }

    @GetMapping("/list")
    public List<User> list() {
        List<User> users = new ArrayList<>();
        users.add(new User("Jhampier", "Tucno"));
        users.add(new User("John", "Doe", "john@gemail.com"));
        users.add(new User("Jane", "Doe", "jane@gmail.com"));

        return users;
    }
}



