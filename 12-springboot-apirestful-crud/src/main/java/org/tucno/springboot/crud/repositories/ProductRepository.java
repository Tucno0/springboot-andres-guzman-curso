package org.tucno.springboot.crud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.crud.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsBySku(String sku);
}
