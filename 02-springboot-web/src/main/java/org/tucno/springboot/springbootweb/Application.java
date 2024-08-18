package org.tucno.springboot.springbootweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
// @PropertySource es una anotación que se utiliza para cargar un archivo de propiedades en un entorno de aplicación Spring.
//@PropertySource("classpath:values.properties")
// @PropertySources es una anotación que se utiliza para cargar varios archivos de propiedades en un entorno de aplicación Spring.
//@PropertySources({
//        @PropertySource("classpath:values.properties"),
//        @PropertySource("classpath:config.properties")
//})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
