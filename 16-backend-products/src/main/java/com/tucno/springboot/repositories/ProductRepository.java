package com.tucno.springboot.repositories;

import com.tucno.springboot.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
