package org.tucno.springboot.springbootweb.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.tucno.springboot.springbootweb.models.User;
import org.tucno.springboot.springbootweb.models.dtos.ParamDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/var")
public class PathVariableController {

    @GetMapping("/baz/{message}")
    // @PathVariable es una anotación que se utiliza para vincular una variable de la URL a un método controlador en Spring MVC.
    // Por ejemplo, en la URL http://localhost:8080/api/var/baz/Hello se envía el parámetro message con el valor Hello
    public ParamDto baz(@PathVariable( name = "message") String mensaje) {
        ParamDto paramDto = new ParamDto();
        paramDto.setMessage(mensaje);
        return paramDto;
    }

    @GetMapping("/mix/{text}/{code}")
    // En este caso se envían dos parámetros en la URL http://localhost:8080/api/var/mix/Hello/200
    public Map<String, Object> bar(@PathVariable String text, @PathVariable Integer code) {
        Map<String, Object> json = new HashMap<>();
        json.put("message", text);
        json.put("code", code);

        return json;
    }

    @PostMapping("/create")
    // @RequestBody es una anotación que se utiliza para vincular el cuerpo de la solicitud web a un método controlador en Spring MVC.
    // En este caso se envía un objeto User en el cuerpo de la solicitud y se devuelve el mismo objeto
    public User create(@RequestBody User user) {
        // Hacer algo con el objeto user recibido
        user.setName(user.getName().toUpperCase());
        return user;
    }
}
