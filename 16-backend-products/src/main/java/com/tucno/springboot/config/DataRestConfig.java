package com.tucno.springboot.config;

import com.tucno.springboot.entities.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// La anotacion @Configuration nos permite definir una clase de configuracion de Spring Boot
@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    // El metodo configureRepositoryRestConfiguration nos permite configurar el servicio REST de Spring Data Rest
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Exponemos el id de las entidades en el servicio REST de Spring Data Rest
        config.exposeIdsFor(Product.class);
    }


}
