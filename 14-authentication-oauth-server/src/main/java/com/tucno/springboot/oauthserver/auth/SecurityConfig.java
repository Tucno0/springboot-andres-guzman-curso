package com.tucno.springboot.oauthserver.auth;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

// La anotation @Configuration indica que la clase es una clase de configuración de Spring Boot, lo que significa que se puede utilizar para configurar la aplicación
@Configuration
// La anotation @EnableWebSecurity habilita la seguridad web en la aplicación Spring Boot y permite configurar la seguridad web
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    // La anotación @Order se utiliza para especificar el orden en el que se aplican las cadenas de filtros de seguridad
    @Order(1)
    // Este método se encarga de configurar la cadena de filtros de seguridad para el servidor de autorización OAuth 2.0
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())    // Enable OpenID Connect 1.0
                )
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    @Bean
    @Order(2)
    // Este método se encarga de configurar la cadena de filtros de seguridad para la aplicación web
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Se deshabilita la protección CSRF para permitir la autenticación de formularios en la aplicación web
                .csrf((csrf) -> csrf.disable())
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    // Este método se encarga de crear un objeto UserDetailsService que se utilizará para la autenticación de los usuarios
    public UserDetailsService userDetailsService() {
        // Se crea un objeto UserDetails con el nombre de usuario, la contraseña y los roles del usuario que se utilizará para la autenticación
        UserDetails userDetails = User.builder()
                .username("pepe")
                .password("{noop}12345") // La contraseña se almacena en texto plano
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    // Este método se encarga de la configuración de los clientes registrados en el servidor de autorización OAuth 2.0
    public RegisteredClientRepository registeredClientRepository() {
        // Cliente 1: Aplicación de Angular
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-app") // Aplicación del cliente (frontend)
                .clientSecret("{noop}12345") // El secreto del cliente es una clave secreta que se utiliza para autenticar al cliente  en el servidor de autorización OAuth 2.0
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // El método de autenticación del cliente es el método utilizado por el cliente para autenticarse en el servidor de autorización OAuth 2.0
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // El tipo de concesión de autorización es el tipo de concesión utilizado por el cliente para obtener un token de acceso
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // Por defecto caduca en 5 minutos
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-app") // La URI de redirección es la URI a la que se redirige el servidor de autorización OAuth 2.0 después de que el usuario haya autorizado al cliente
                .redirectUri("http://127.0.0.1:8080/authorized") // Endpoint en el lado del cliente para poder recuperar el codigo de autorizacion
                .postLogoutRedirectUri("http://127.0.0.1:8080/logout") // La URI de redirección después de la salida es la URI a la que se redirige el servidor de autorización OAuth 2.0 después de que el usuario haya salido
                .scope("read") // con ROL read se puede leer la información del usuario autenticado (NORMAL)
                .scope("write") // con ROL write se puede escribir la información del usuario autenticado (ADMIN)
                .scope(OidcScopes.OPENID) // Aqui se define el alcance de la autorización, OPENID es un alcance especial que se utiliza para la autenticación de OpenID Connect
                .scope(OidcScopes.PROFILE) // Aqui se define el alcance de la autorización, PROFILE es un alcance especial que se utiliza para obtener información de perfil del usuario
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        // Cliente 2: Aplicación de React

        return new InMemoryRegisteredClientRepository(oidcClient);
    }

    @Bean
    // Este método se encarga de la configuración de las claves RSA utilizadas para firmar y verificar los tokens JWT
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // Este método se encarga de generar un par de claves RSA para firmar y verificar los tokens JWT
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    // Este método se encarga de crear un objeto JwtDecoder que se utilizará para decodificar los tokens JWT
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    // Este método se encarga de la configuración del servidor de autorización OAuth 2.0
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}