package org.tucno.springboot.springbootdi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tucno.springboot.springbootdi.models.Product;
import org.tucno.springboot.springbootdi.services.ProductService;
import org.tucno.springboot.springbootdi.services.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SomeController {

    // @Autowired es una anotación que se utiliza para inyectar dependencias en Spring automáticamente
    // Inyección de dependencias por campo
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product show(@PathVariable Long id) {
        return productService.findById(id);
    }
}
