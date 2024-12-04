package org.tucno.springboot.security.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tucno.springboot.security.entities.Product;
import org.tucno.springboot.security.services.ProductService;
import org.tucno.springboot.security.validators.groups.OnCreate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// La anotación @CrossOrigin se utiliza para permitir las solicitudes de recursos de origen cruzado (CORS) desde el origen http://localhost:4200 a la ruta /products
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    private ProductValidator productValidator;

    @GetMapping
    // La anotación @PreAuthorize se utiliza para definir reglas de autorización en los métodos de un controlador
    // Se ejecuta antes de que se ejecute el método y se encarga de comprobar si el usuario tiene los roles necesarios para acceder al método
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Se requiere que el usuario tenga el rol ADMIN o USER para acceder a la ruta /products
    public List<Product> findAll() {
        return productService.findAll();
    }

    // @GetMapping indica que el método se ejecuta cuando se hace una petición GET
    // @PathVariable indica que el valor de la variable id se recibe en la URL
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Se requiere que el usuario tenga el rol ADMIN o USER para acceder a la ruta /products
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    // @PostMapping indica que el método se ejecuta cuando se hace una petición POST
    // ResponseEntity<Product> indica que el método devuelve un objeto de tipo Product
    // @RequestBody indica que el objeto product se va a recibir en el cuerpo de la petición
    // @Valid valida el objeto product con las anotaciones de validación de la clase Product
    // @Validated(OnCreate.class) indica que se van a validar las anotaciones de validación del grupo OnCreate de la clase Product
    // BindingResult result almacena los errores de validación
    @PreAuthorize("hasRole('ADMIN')") // Se requiere que el usuario tenga el rol ADMIN para acceder a la ruta /products mediante el método POST
    @PostMapping
    public ResponseEntity<?> save(@Validated(OnCreate.class) @RequestBody Product product, BindingResult result) {
        // Se valida el objeto product con el validador productValidator
//        productValidator.validate(product, result);

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    // @PutMapping indica que el método se ejecuta cuando se hace una petición PUT
    // ResponseEntity<?> indica que el método devuelve un objeto de cualquier tipo
    // @RequestBody indica que el objeto product se va a recibir en el cuerpo de la petición
    // @Valid valida el objeto product con las anotaciones de validación de la clase Product
    // BindingResult result almacena los errores de validación
    // @PathVariable indica que el valor de la variable id se recibe en la URL
    // El BindingResult debe ir después del objeto que se va a validar
    @PreAuthorize("hasRole('ADMIN')") // Se requiere que el usuario tenga el rol ADMIN para acceder a la ruta /products/{id} mediante el método PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> update( @Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
        // Se valida el objeto product con el validador productValidator
//        productValidator.validate(product, result);

        // Si hay errores de validación, se devuelven los errores
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product productUpdated = productOptional.get();
            productUpdated.setSku(product.getSku());
            productUpdated.setName(product.getName());
            productUpdated.setPrice(product.getPrice());
            productUpdated.setDescription(product.getDescription());
            // Preferible usar el método update de la clase ProductService
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productUpdated));
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> productOptional = productService.delete(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
