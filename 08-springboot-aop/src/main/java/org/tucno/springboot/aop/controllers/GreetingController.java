package org.tucno.springboot.aop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tucno.springboot.aop.services.GreetingService;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @Autowired
    private GreetingService greatingService;

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {
        // El aspecto se ejecutará antes y después de este método
        return ResponseEntity.ok(Collections.singletonMap("greeting", greatingService.sayHello("World", "Good morning!")));
    }

    @GetMapping("/error")
    public ResponseEntity<?> error() {
        // El aspecto se ejecutará antes y después de este método
        return ResponseEntity.ok(Collections.singletonMap("error", greatingService.errorMethod("World", "Good morning!")));
    }
}
