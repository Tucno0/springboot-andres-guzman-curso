package com.tucno.springboot.oauthclient.controllers;

import com.tucno.springboot.oauthclient.models.Message;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/list") // READ y WRITE
    public List<Message> list() {
        return Collections.singletonList(new Message("Test list"));
    }

    @PostMapping("/create") // WRITE
    public Message create(@RequestBody Message message) {
        System.out.println("Mensaje guardado: " + message);
        return message;
    }

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code) {
        return Collections.singletonMap("code", code);
    }
}
