package org.tucno.springboot.springbootweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home", "/index", ""})
    public String home() {

        // redirect: se usa para redirigir a otra URL o a otro controlador
        // genera una nueva petición a la URL o controlador especificado
//        return "redirect:/list";

        // forward: se usa para redirigir a otra URL o a otro controlador
        // reenvía la petición a la URL o controlador especificado
        return "forward:/list";
    }
}
