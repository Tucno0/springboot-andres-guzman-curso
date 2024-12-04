package org.tucno.springboot.security.security;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.tucno.springboot.security.security.filters.JwtAuthenticationFilter;
import org.tucno.springboot.security.security.filters.JwtValidationFilter;

import java.util.Arrays;

// La anotación @Configuration indica que esta clase es una clase de configuración de Spring y que se debe cargar al inicio de la aplicación
// nos permite definir beans de Spring y configurar el contexto de Spring en nuestra aplicación
@Configuration

// La anotación @EnableMethodSecurity se utiliza para habilitar la seguridad basada en métodos en la aplicación de Spring
// Esto nos permite definir reglas de seguridad en los métodos de los controladores utilizando las anotaciones @PreAuthorize y @PostAuthorize
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    // Inyectamos del filtro JwtAuthenticationFilter
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        // Obtenemos el authenticationManger de nuestra aplicacion de SpringSecurity
        return authenticationConfiguration.getAuthenticationManager();
    }

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
                                .requestMatchers(HttpMethod.GET, "/api/users") // Se configura la ruta /users para que solo se pueda acceder a ella mediante el método GET
                                .permitAll() // Se permite el acceso a la ruta /users mediante el método GET sin necesidad de autenticación

                                .requestMatchers(HttpMethod.POST, "/api/users/register")
                                .permitAll()

//                                .requestMatchers(HttpMethod.POST, "/api/users") // Se configura la ruta /users para que solo se pueda acceder a ella mediante el método POST
//                                // Solo se pone el rol ADMIN y no ROLE_ADMIN porque Spring Security añade automáticamente el prefijo ROLE_ a los roles
//                                .hasRole("ADMIN") // Se requiere que el usuario tenga el rol ADMIN para acceder a la ruta /users mediante el método POST
//
//                                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{id}")
//                                .hasAnyRole("USER", "ADMIN") // Se requiere que el usuario tenga el rol USER o ADMIN para acceder a las rutas /products y /products/{id} mediante el método GET
//
//                                .requestMatchers(HttpMethod.POST, "/api/products")
//                                .hasRole("ADMIN")
//
//                                .requestMatchers(HttpMethod.PUT, "/api/products/{id}")
//                                .hasRole("ADMIN")
//
//                                .requestMatchers(HttpMethod.DELETE, "/api/products/{id}")
//                                .hasRole("ADMIN")

                                .anyRequest() // Se configura cualquier otra ruta de la aplicación
                                .authenticated() // Se requiere autenticación con JWT para acceder a cualquier otra ruta de la aplicación
                )
                // El método addFilter() se encarga de añadir un filtro de seguridad a la cadena de filtros de seguridad de la aplicación
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // Se añade el filtro JwtAuthenticationFilter a la cadena de filtros de seguridad de la aplicación
                .addFilter(new JwtValidationFilter(authenticationManager())) // Se añade el filtro JwtValidationFilter a la cadena de filtros de seguridad de la aplicación
                // El método csrf() se encarga de deshabilitar la protección CSRF (Cross-Site Request Forgery)
                // CSRF es un ataque que se produce cuando un atacante engaña a un usuario para que realice una acción no deseada en una aplicación web en la que el usuario está autenticado
                .csrf(config -> config.disable()) // Se deshabilita la protección CSRF en la aplicación
                // El método cors() se encarga de configurar la política de CORS (Cross-Origin Resource Sharing) de la aplicación mediante la definición de una fuente de configuración de CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Se configura la configuración de CORS de la aplicación
                // El método sessionManagement() se encarga de configurar la gestión de sesiones de la aplicación mediante la definición de una política de creación de sesiones
                // SessionCreationPolicy.STATELESS indica que no se creará una sesión HTTP para la autenticación de los usuarios
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Se configura la política de creación de sesiones como STATELESS
                // El método build() se encarga de construir y devolver un objeto SecurityFilterChain que define la cadena de filtros de seguridad que se aplicarán a las solicitudes entrantes
                .build(); // Se construye y se devuelve la cadena de filtros de seguridad
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Se crea un objeto CorsConfiguration que define la configuración de CORS (Cross-Origin Resource Sharing) de la aplicación
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Se configura la lista de orígenes permitidos para las solicitudes CORS en la aplicación
        corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
        // Se configura la lista de métodos HTTP permitidos para las solicitudes CORS en la aplicación
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // Se configura la lista de encabezados permitidos para las solicitudes CORS en la aplicación
        corsConfig.setAllowedHeaders(Arrays.asList("*", "Authorization", "Content-Type"));
        // Se configura si se permiten las credenciales en las solicitudes CORS en la aplicación
        corsConfig.setAllowCredentials(true);

        // Se crea un objeto UrlBasedCorsConfigurationSource que define la configuración de CORS basada en URL de la aplicación
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Se añade la configuración de CORS al objeto UrlBasedCorsConfigurationSource
        source.registerCorsConfiguration("/**", corsConfig);

        // Se devuelve el objeto UrlBasedCorsConfigurationSource que define la configuración de CORS de la aplicación
        return source;
    }

    // La anotación @Bean indica que el método anotado producirá un bean que se registrará en el contexto de Spring
    @Bean
    // FilterRegistrationBean es una clase que nos permite definir la configuración de un filtro en la aplicación y registrar el filtro en el contexto de Spring
    FilterRegistrationBean<CorsFilter> corsFilter() {
        // Se crea un objeto FilterRegistrationBean que define la configuración de un filtro de CORS en la aplicación
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter());
        // Se configura el orden de ejecución del filtro de CORS en la aplicación (Alta prioridad)
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        // Se devuelve el objeto FilterRegistrationBean que define la configuración del filtro de CORS de la aplicación
        return corsBean;
    }
}
