package org.tucno.springboot.crud.services;

import org.tucno.springboot.crud.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product>  findById(Long id);

    Product save(Product product);

    Optional<Product>  delete(Long id);

    boolean existsBySku(String sku);
}
