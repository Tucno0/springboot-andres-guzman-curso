package org.tucno.springboot.interceptors.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {

    @GetMapping("/foo")
    public Map<String, String> foo() {
        return Map.of("message", "Handler foo");
    }

    @GetMapping("/bar")
    public Map<String, String> bar() {
        return Map.of("message", "Handler bar");
    }

    @GetMapping("/baz")
    public Map<String, String> baz() {
        return Map.of("message", "Handler baz");
    }
}
