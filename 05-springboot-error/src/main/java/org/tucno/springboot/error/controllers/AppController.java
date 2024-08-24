package org.tucno.springboot.error.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tucno.springboot.error.exceptions.UserNotFoundException;
import org.tucno.springboot.error.models.domain.User;
import org.tucno.springboot.error.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    // http://localhost:8080/app
    @GetMapping()
    public String index() {
//        int value = 10 / 0; // ArithmeticException
        int value = Integer.parseInt("10d"); // NumberFormatException

        System.out.println("value = " + value);
        return "ok 200";
    }

    // http://localhost:8080/app/error
    @GetMapping("/show/{id}")
//    public ResponseEntity<?> show(@PathVariable Long id) {
    public User show(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

//        Optional<User> user = userService.findById(id);
//        if (user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user.get());
    }
}
