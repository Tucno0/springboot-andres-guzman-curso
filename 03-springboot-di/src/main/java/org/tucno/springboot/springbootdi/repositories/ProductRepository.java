package org.tucno.springboot.springbootdi.repositories;

import org.tucno.springboot.springbootdi.models.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(Long id);
}
