package com.tucno.springboot.repositories;

import com.tucno.springboot.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:5173"})
// La anotation @RepositoryRestResource nos permite exponer los metodos de este repositorio como un servicio REST de Spring Data Rest
// El path nos permite definir la URL de acceso a este servicio REST
// En este caso, la URL de acceso a este servicio REST será http://localhost:8080/api/products
// Ya no es necesario crear un controlador para exponer los metodos de este repositorio como un servicio REST
@RepositoryRestResource(path = "products")
public interface ProductRepository extends CrudRepository<Product, Long> {
}
