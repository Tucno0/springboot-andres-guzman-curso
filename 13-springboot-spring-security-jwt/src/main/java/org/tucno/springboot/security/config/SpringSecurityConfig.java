package org.tucno.springboot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// La anotación @Configuration indica que esta clase es una clase de configuración de Spring y que se debe cargar al inicio de la aplicación
// nos permite definir beans de Spring y configurar el contexto de Spring en nuestra aplicación
@Configuration
public class SpringSecurityConfig {

    // La anotación @Bean indica que el método anotado producirá un bean que se registrará en el contexto de Spring
    @Bean
    // PasswordEncoder es una interfaz que proporciona un solo método encode() que se utiliza para cifrar la contraseña
    // En este método se crea un bean de tipo BCryptPasswordEncoder y se devuelve para que Spring lo registre en el contexto de la aplicación
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder es una implementación de PasswordEncoder que utiliza el algoritmo de cifrado BCrypt para cifrar la contraseña
        return new BCryptPasswordEncoder();
    }

    @Bean
    // SecurityFilterChain es una interfaz que nos permite definir una cadena de filtros de seguridad que se aplicarán a las solicitudes entrantes
    // El metodo filterChain() se encarga de configurar la seguridad de la aplicación y devolver un objeto SecurityFilterChain
    // que define la cadena de filtros de seguridad que se aplicarán a las solicitudes entrantes
    // HttpSecurity es una clase que nos permite configurar la seguridad de la aplicación mediante la definición de reglas de seguridad
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // El método authorizeHttpRequests() se encarga de configurar las reglas de seguridad de la aplicación
        // Recibe un Consumer<AuthorizationRequest> que se encarga de configurar las reglas de autorización
        return http.authorizeHttpRequests(
                        (authorizeRequests) -> authorizeRequests
                                .requestMatchers("/api/users") // Se configura la ruta /users para que sea accesible por cualquier usuario
                                .permitAll() // Se permite el acceso a la ruta /users sin autenticación
                                .anyRequest() // Se configura cualquier otra ruta de la aplicación
                                .authenticated() // Se requiere autenticación para acceder a cualquier otra ruta de la aplicación
                )
                // El método csrf() se encarga de deshabilitar la protección CSRF (Cross-Site Request Forgery)
                // CSRF es un ataque que se produce cuando un atacante engaña a un usuario para que realice una acción no deseada en una aplicación web en la que el usuario está autenticado
                .csrf(config -> config.disable()) // Se deshabilita la protección CSRF en la aplicación
                // El método sessionManagement() se encarga de configurar la gestión de sesiones de la aplicación mediante la definición de una política de creación de sesiones
                // SessionCreationPolicy.STATELESS indica que no se creará una sesión HTTP para la autenticación de los usuarios
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Se configura la política de creación de sesiones como STATELESS
                // El método build() se encarga de construir y devolver un objeto SecurityFilterChain que define la cadena de filtros de seguridad que se aplicarán a las solicitudes entrantes
                .build(); // Se construye y se devuelve la cadena de filtros de seguridad
    }
}
