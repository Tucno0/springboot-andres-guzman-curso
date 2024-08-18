package org.tucno.springboot.springbootweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.tucno.springboot.springbootweb.models.User;

import java.util.List;

// La anotación @Controller indica que la clase es un controlador de Spring MVC y que manejará las peticiones HTTP
// Solo se debe usar en clases que manejen las vistas de la aplicación web
@Controller
public class UserController {

    @GetMapping("/details")
    public String details(Model model) {
        User user = new User("Jhampier", "Tucno");

        model.addAttribute("title", "Hola mundo Spring boot");
        model.addAttribute("description", "Esto es una descripción de la página de detalles");
        model.addAttribute("user", user);

        return "details";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = List.of(
            new User("Jhampier", "Tucno"),
            new User("John", "Doe", "john@gemail.com"),
            new User("Jane", "Doe", "jane@gmail.com")
        );

        model.addAttribute("title", "Lista de usuarios");
//        model.addAttribute("users", users);

        return "list";
    }

    // La anotación @ModelAttribute indica que el método proporciona datos que se usarán en la vista de la página a nivel global
    // se podrá acceder a los datos en la vista con el nombre del método
    @ModelAttribute("users")
    public List<User> users() {
        return List.of(
                new User("Jhampier", "Tucno"),
                new User("John", "Doe", "john@gemail.com"),
                new User("Jane", "Doe", "jane@gmail.com")
        );
    }
}
