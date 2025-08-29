package com.example.authtemplate.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for handling JWT operations.
 *
 * <p>Provides methods to generate, validate, and extract claims from JWT tokens.
 * Configurable secret key and expiration time are injected from application properties.</p>
 */
@Component
public class JwtUtil {

    // Secret key for signing the JWT (injected from application.properties)
    @Value("${jwt.secret}")
    private String secret;

    // Token expiration time in milliseconds (injected from application.properties)
    @Value("${jwt.expirationMs}")
    private long expirationMs;

    // Generate the signing key from the configured secret
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate a JWT token for the given username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // set user identity
                .setIssuedAt(new Date()) // token issue time
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // expiry
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign with secret
                .compact();
    }

    // Extract the username (subject) from a JWT token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate the integrity and expiration of a JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // will throw if invalid or expired
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
