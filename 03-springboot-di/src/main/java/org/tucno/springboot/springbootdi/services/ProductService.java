package org.tucno.springboot.springbootdi.services;

import org.tucno.springboot.springbootdi.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
}
