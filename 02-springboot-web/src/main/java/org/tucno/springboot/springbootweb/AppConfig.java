package org.tucno.springboot.springbootweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

// @Configuration es una anotación de marcador que indica que una clase declara uno o más métodos @Bean y puede ser procesada por el contenedor de Spring para generar definiciones de bean y solicitudes de servicio para esos beans en tiempo de ejecución.
@Configuration
// @PropertySource es una anotación que se utiliza para cargar un archivo de propiedades en un entorno de aplicación Spring.
// @PropertySources es una anotación que se utiliza para cargar varios archivos de propiedades en un entorno de aplicación Spring.
@PropertySources({
        @PropertySource( value = "classpath:values.properties", encoding = "UTF-8"),
//        @PropertySource("classpath:config.properties")
})
public class AppConfig {
}
