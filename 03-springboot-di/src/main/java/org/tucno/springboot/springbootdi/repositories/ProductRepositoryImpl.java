package org.tucno.springboot.springbootdi.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.tucno.springboot.springbootdi.models.Product;

import java.util.Arrays;
import java.util.List;

// @Component es una anotación que se utiliza para indicar que la clase es un componente de Spring y se debe registrar en el contenedor de Spring
//@Component

// @Repository es casi lo mismo que @Component, pero se utiliza para indicar que la clase es un repositorio de Spring y se debe registrar en el contenedor de Spring
// Un repositorio es una clase que se utiliza para acceder a una fuente de datos, como una base de datos o una API
// Le podemos dar un nombre al bean en el contenedor de Spring con la propiedad value de la anotación @Repository
// Si ponemos el nombre del bean entonces vamos a tener que utilizar ese nombre para inyectar el bean en otras clases
@Repository("productRepositoryImpl")

// @Primary es una anotación que se utiliza para indicar que una implementación de una interfaz es la principal y se debe utilizar cuando hay varias implementaciones de una interfaz
// En este caso, se utiliza para indicar que ProductRepositoryFoo es la implementación principal de la interfaz ProductRepository
@Primary

// por defecto el nombre de la clase es el nombre del bean en el contenedor de Spring pero con la primera letra en minúscula
// En este caso, el nombre del bean en el contenedor de Spring es productRepositoryImpl

// @RequestScope es una anotación que se utiliza para indicar que el bean debe tener un alcance de solicitud en Spring
// Un alcance de solicitud significa que se crea una instancia del bean por cada solicitud HTTP y se destruye al final de la solicitud
//@RequestScope

// @SessionScope es una anotación que se utiliza para indicar que el bean debe tener un alcance de sesión en Spring
// Un alcance de sesión significa que se crea una instancia del bean por cada sesión HTTP y se destruye al final de la sesión, por ejemplo, cuando se cierra el navegador web
//@SessionScope

// por defecto el alcance del bean es singleton
public class ProductRepositoryImpl implements ProductRepository {
    private List<Product> data;

    public ProductRepositoryImpl() {
        this.data = Arrays.asList(
            new Product(1L, "Memory Card", 500.0),
            new Product(2L, "Pen Drive", 750.0),
            new Product(3L, "Power Bank", 1500.0),
            new Product(4L, "Wireless Headphone", 1850.0),
            new Product(5L, "Bluetooth Speaker", 2000.0),
            new Product(6L, "Smart Watch", 3000.0),
            new Product(7L, "Fitness Band", 2500.0),
            new Product(8L, "Charging Cable", 150.0),
            new Product(9L, "Mobile Cover", 100.0),
            new Product(10L, "Screen Guard", 50.0)
        );
    }

    @Override
    public List<Product> findAll() {
        return data;
    }

    @Override
    public Product findById(Long id) {
        return data.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }
}