package org.tucno.springboot.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * WebMvcConfigurer es una interfaz que proporciona métodos de callback para personalizar la configuración de Spring MVC.
     * Por lo general, se implementa para agregar recursos manejadores, convertidores de formato, argumentos de método personalizados y otros.
     */

    @Autowired
    @Qualifier("loadingTimeInterceptor")
    private HandlerInterceptor loadingTimeInterceptor;

    // El método addInterceptors() se utiliza para registrar un interceptor en la aplicación.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Si no se agrega ninguna ruta, el interceptor se ejecutará en todas las rutas
//        registry.addInterceptor(loadingTimeInterceptor);

        // addPathPatterns se utiliza para agregar las rutas que se van a interceptar con el interceptor
//        registry.addInterceptor(loadingTimeInterceptor).addPathPatterns("/app/bar", "/app/foo");

        // excludePathPatterns se utiliza para excluir las rutas que no se van a interceptar con el interceptor
//        registry.addInterceptor(loadingTimeInterceptor).excludePathPatterns("/app/bar", "/app/foo");

        // Se ejecuta el interceptor en todas las rutas que empiecen con /app/
        registry.addInterceptor(loadingTimeInterceptor).addPathPatterns("/app/**");
    }
}
