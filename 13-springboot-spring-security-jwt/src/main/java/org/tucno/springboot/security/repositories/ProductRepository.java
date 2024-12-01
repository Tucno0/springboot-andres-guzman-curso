package org.tucno.springboot.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.security.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsBySku(String sku);
}
