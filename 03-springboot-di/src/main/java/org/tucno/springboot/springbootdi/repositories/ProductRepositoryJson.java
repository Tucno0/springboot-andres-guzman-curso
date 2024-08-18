package org.tucno.springboot.springbootdi.repositories;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.tucno.springboot.springbootdi.models.Product;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProductRepositoryJson implements ProductRepository {
    private List<Product> products;

    public ProductRepositoryJson() {
        // Resource es una interfaz de Spring que representa un recurso en la aplicación
        // ClassPathResource es una clase de Spring que nos permite acceder a recursos en el classpath de nuestra aplicación
        Resource resource = new ClassPathResource("json/product.json");
        readValueJson(resource);
    }

    public ProductRepositoryJson(Resource resource) {
        readValueJson(resource);
    }

    private void readValueJson(Resource resource) {
        // ObjectMapper es una clase de Jackson que nos permite convertir JSON a objetos Java y viceversa
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leemos el archivo JSON y lo convertimos a una lista de objetos Product
            products = Arrays.asList(objectMapper.readValue(resource.getInputStream(), Product[].class));
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
