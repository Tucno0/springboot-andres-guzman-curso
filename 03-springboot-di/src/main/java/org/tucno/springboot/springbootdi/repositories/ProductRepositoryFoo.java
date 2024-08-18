package org.tucno.springboot.springbootdi.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.tucno.springboot.springbootdi.models.Product;

import java.util.Collections;
import java.util.List;

// @Repository es casi lo mismo que @Component, pero se utiliza para indicar que la clase es un repositorio de Spring y se debe registrar en el contenedor de Spring
// Un repositorio es una clase que se utiliza para acceder a una fuente de datos, como una base de datos o una API
@Repository

// @Primary es una anotación que se utiliza para indicar que una implementación de una interfaz es la principal y se debe utilizar cuando hay varias implementaciones de una interfaz
// En este caso, se utiliza para indicar que ProductRepositoryFoo es la implementación principal de la interfaz ProductRepository
//@Primary
public class ProductRepositoryFoo  implements ProductRepository {

    @Override
    public List<Product> findAll() {
        // Collections es una clase de utilidad de Java que proporciona métodos estáticos para trabajar con colecciones
        // El método singletonList de la clase Collections se utiliza para crear una lista inmutable que contiene un solo elemento
        return Collections.singletonList(new Product(1L, "Memory Card", 500.0));
    }

    @Override
    public Product findById(Long id) {
        return new Product(1L, "Memory Card", 500.0);
    }
}
