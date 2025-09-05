package com.example.authtemplate.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for handling JWT operations.
 *
 * <p>Provides methods to generate, validate, and extract claims from JWT tokens.
 * Configurable secret key and expiration time are injected from application properties.</p>
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate a JWT token for the given username with roles
    public String generateToken(String username, String... roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .claim("roles", roles) // embed roles in token
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract the username (subject) from a JWT token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract roles from the token
    public String[] extractRoles(String token) {
        Object roles = extractAllClaims(token).get("roles");
        if (roles instanceof String[]) {
            return (String[]) roles;
        } else if (roles instanceof java.util.List) {
            return ((java.util.List<?>) roles).toArray(new String[0]);
        }
        return new String[0];
    }

    // Validate the integrity and expiration of a JWT token
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // will throw if invalid or expired
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Helper: extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
