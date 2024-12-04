package org.tucno.springboot.security.security.filters;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.tucno.springboot.security.entities.User;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.tucno.springboot.security.security.TokenJwtConfig.*;

/**
 * FILTERS EN SPRING SECURITY
 * Los filtros en Spring Security son clases que se encargan de interceptar las peticiones HTTP y realizar alguna acción sobre ellas, como autenticar al usuario, autorizar la petición, etc.
 * Se ejecutan en un orden específico y permiten personalizar el comportamiento de la seguridad de la aplicación.
 * Algunos de los filtros más comunes en Spring Security son:
 * - UsernamePasswordAuthenticationFilter: se encarga de autenticar al usuario a partir de un nombre de usuario y una contraseña.
 * - BasicAuthenticationFilter: se encarga de autenticar al usuario mediante autenticación básica.
 * - JwtAuthenticationFilter: se encarga de autenticar al usuario mediante un token JWT.
 * - JwtAuthorizationFilter: se encarga de autorizar al usuario mediante un token JWT.
 * - CorsFilter: se encarga de configurar la política de intercambio de recursos entre diferentes orígenes (CORS).
 * - CsrfFilter: se encarga de proteger la aplicación contra ataques CSRF (Cross-Site Request Forgery).
 * - SessionManagementFilter: se encarga de gestionar las sesiones de la aplicación.
 * - ExceptionTranslationFilter: se encarga de gestionar las excepciones producidas durante la autenticación y autorización de los usuarios.
 * - LogoutFilter: se encarga de gestionar la finalización de la sesión de un usuario.
 * - RequestCacheAwareFilter: se encarga de gestionar la caché de peticiones de la aplicación.
 * - SecurityContextHolderAwareRequestFilter: se encarga de gestionar el contexto de seguridad de las peticiones.
 * - RememberMeAuthenticationFilter: se encarga de autenticar al usuario mediante la autenticación "recuérdame".
 * - AnonymousAuthenticationFilter: se encarga de autenticar al usuario de forma anónima.
 * - SessionManagementFilter: se encarga de gestionar las sesiones de la aplicación.
 * - ConcurrentSessionFilter: se encarga de gestionar las sesiones concurrentes de los usuarios.
 * - X509AuthenticationFilter: se encarga de autenticar al usuario mediante certificados X.509.
 * - PreAuthenticatedAuthenticationFilter: se encarga de autenticar al usuario mediante autenticación previa.
 * - SwitchUserFilter: se encarga de permitir a un usuario autenticado cambiar de usuario.
 * - SecurityContextPersistenceFilter: se encarga de persistir el contexto de seguridad de las peticiones.
 * - HeaderWriterFilter: se encarga de escribir encabezados HTTP en las respuestas.
 */

// La clase JwtAuthenticationFilter extiende de UsernamePasswordAuthenticationFilter, que es un filtro de autenticación que se encarga de autenticar al usuario a partir de un nombre de usuario y una contraseña
// En este caso usando la clase UsernamePasswordAuthenticationFilter como base para nuestro filtro de autenticación JWT (Json Web Token)
// Tenemos que registrar este filtro en la configuración de Spring Security para que se ejecute en el proceso de autenticación
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // AuthenticationManager es una interfaz que se encarga de autenticar a los usuarios en el sistema de seguridad de Spring Security
    private AuthenticationManager authenticationManager;

    // Constructor de la clase JwtAuthenticationFilter que recibe un AuthenticationManager como parámetro
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // El método attemptAuthentication se encarga de intentar autenticar al usuario a partir de la petición HTTP recibida
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Initializamos las variables user, username y password
        User user = null;
        String username = null;
        String password = null;

        // Intentamos leer el usuario de la petición HTTP
        try {
            // Leemos el usuario de la petición HTTP y lo mapeamos a la clase User
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            // Obtenemos el nombre de usuario y la contraseña del usuario
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) { // Si se produce un error al leer el usuario de la petición HTTP, lanzamos una excepción
            throw new RuntimeException(e);
        } catch (DatabindException e) { // Si se produce un error al mapear el usuario a la clase User, lanzamos una excepción
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e); // Si se produce un error de entrada/salida, lanzamos una excepción
        }

        // Si el usuario no es nulo, obtenemos el nombre de usuario y la contraseña del usuario
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // Devolvemos el resultado de la autenticación del usuario
        // Por debajo se llamará al método loadUserByUsername de la clase JpaUserDetailsService para cargar el usuario a partir del nombre de usuario
        return authenticationManager.authenticate(authenticationToken);
    }

    // El método successfulAuthentication se encarga de realizar alguna acción cuando la autenticación del usuario ha sido exitosa (por ejemplo, generar un token JWT)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Obtenemos el usuario autenticado a partir del resultado de la autenticación
        // Casteamos el resultado de la autenticación a un objeto User de Spring Security
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        // Obtenemos el nombre de usuario del usuario autenticado
        String username = user.getUsername();
        // Obtenemos los roles del usuario autenticado (autoridades)
        Collection<? extends GrantedAuthority> roles =  authResult.getAuthorities();

        // Creamos un objeto Claims con las autoridades del usuario
        // Los claims son los datos que se incluyen en el token JWT (por ejemplo, el nombre de usuario, la fecha de expiración, etc.)
        // Agregamos las autoridades del usuario al objeto Claims con la clave "authorities" y las construimos con .build()
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles)) // con .add() se añaden las autoridades del usuario al objeto Claims
                .add("username", username) // con .add() se añade el nombre de usuario al objeto Claims
                .build();

        // Generamos un token JWT a partir del nombre de usuario y la clave secreta
        String token = Jwts.builder() // con .builder() se crea un nuevo token JWT
                .subject(username) // con .subject() se establece el nombre de usuario como sujeto del token
                .claims(claims) // con .claims() se añaden los claims al token (en este caso, las autoridades del usuario)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // con .expiration() se establece la fecha de expiración del token (1 hora)
                .issuedAt(new Date()) // con .issuedAt() se establece la fecha de emisión del token
                .signWith(SECRET_KEY) // con .signWith() se firma el token con la clave secreta
                .compact(); // con .compact() se compacta el token en una cadena de texto

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token); // con .addHeader() se añade el token JWT a la cabecera de la respuesta HTTP

        // Creamos un mapa con el token, el nombre de usuario y un mensaje de éxito
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hola %s, has iniciado sesión con éxito", username));

        // Convertimos el mapa a un JSON y lo escribimos en la respuesta HTTP
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200); // con .setStatus() se establece el código de estado de la respuesta HTTP
        response.setContentType(CONTENT_TYPE); // con .setContentType() se establece el tipo de contenido de la respuesta HTTP
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // Creamos un mapa con un mensaje de error
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error de autenticación, username o password incorrectos!");
        body.put("error", failed.getMessage());

        // Convertimos el mapa a un JSON y lo escribimos en la respuesta HTTP
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401); // con .setStatus() se establece el código de estado de la respuesta HTTP
        response.setContentType(CONTENT_TYPE); // con .setContentType() se establece el tipo de contenido de la respuesta HTTP
    }
}
