package org.tucno.springboot.springbootdi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.tucno.springboot.springbootdi.repositories.ProductRepository;
import org.tucno.springboot.springbootdi.repositories.ProductRepositoryJson;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    // Otra forma de inyectar propiedades en Spring es utilizando la anotación @Value
    // en este caso, se inyecta el archivo JSON que contiene los productos
    @Value("classpath:json/product.json")
    private Resource resource;

    // Otra forma alternativa de crear un componente en Spring
    // La anotación @Bean indica que el método que la contiene va a devolver un objeto que debe ser registrado en el contenedor de Spring
    // Es util para registrar objetos que no son instancias de clases que nosotros creamos, como por ejemplo objetos de clases de terceros o de Java

    @Bean("productRepositoryJson")
    public ProductRepository productRepositoryJson() {
        return new ProductRepositoryJson(resource);
    }
}
