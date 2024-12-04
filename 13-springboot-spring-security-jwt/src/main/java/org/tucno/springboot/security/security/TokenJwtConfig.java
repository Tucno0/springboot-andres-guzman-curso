package org.tucno.springboot.security.security;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    // SECRET_KEY es una clave secreta que se utiliza para firmar y verificar los tokens JWT
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}
