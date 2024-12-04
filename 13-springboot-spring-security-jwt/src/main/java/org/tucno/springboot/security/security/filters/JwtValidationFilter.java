package org.tucno.springboot.security.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.tucno.springboot.security.security.SimpleGrantedAuthorityJsonCreator;

import static org.tucno.springboot.security.security.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // El método doFilterInternal() se encarga de realizar la validación del token JWT en las solicitudes entrantes a la aplicación
    // Recibe como parámetros la solicitud HTTP, la respuesta HTTP y la cadena de filtros de seguridad que se aplicarán a las solicitudes entrantes
    // El método se encarga de validar el token JWT en la cabecera Authorization de la solicitud HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // Si la cabecera Authorization no contiene el prefijo Bearer, se finaliza la validación del token JWT y se continúa con la cadena de filtros de seguridad
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response); // Se continúa con la cadena de filtros de seguridad
            return;
        }

        // Se obtiene el token JWT de la cabecera Authorization de la solicitud HTTP
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            // Se verifica y se obtienen los claims del token JWT utilizando la clave secreta SECRET_KEY
            Claims claims = Jwts.parser() // Se crea un objeto JwtParser para verificar y parsear el token JWT
                    .verifyWith(SECRET_KEY) // Se verifica el token JWT utilizando la clave secreta SECRET_KEY
                    .build() // Se construye el objeto JwtParser para verificar y parsear el token JWT
                    .parseSignedClaims(token) // Se parsean los claims del token JWT verificado
                    .getPayload(); // Se obtienen los claims del token JWT verificado

            String username = claims.getSubject(); // Se obtiene el nombre de usuario del token JWT
//            String username2 = (String) claims.get("username"); // Se obtiene el nombre de usuario del token JWT
            Object authoritiesClaims = claims.get("authorities"); // Se obtienen los roles del token JWT

            // Se crea una colección de GrantedAuthority a partir de los roles obtenidos del token JWT utilizando la clase SimpleGrantedAuthority
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper() // Se crea un objeto ObjectMapper para convertir los roles en un array de SimpleGrantedAuthority
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class) // Se añade la anotación @JsonCreator a la clase SimpleGrantedAuthority
                            .readValue(authoritiesClaims.toString().getBytes(), // Se convierten los roles en un array de SimpleGrantedAuthority utilizando el ObjectMapper
                    SimpleGrantedAuthority[].class) // Se especifica el tipo de array de SimpleGrantedAuthority
            );

            // Se crea un objeto UsernamePasswordAuthenticationToken con el nombre de usuario y los roles obtenidos del token JWT
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Se establece la autenticación en el contexto de seguridad de Spring

            // Se continúa con la cadena de filtros de seguridad
            chain.doFilter(request, response); // Se continúa con la cadena de filtros de seguridad

        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();

            body.put("error", e.getMessage());
            body.put("message", "Invalid token JWT");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);

        }
    }
}
