package com.example.authtemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for authentication responses.
 *
 * <p>This object is returned to the client after a successful login or registration
 * and contains the generated JWT access token along with basic user details.</p>
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    // JWT access token for authentication
    private String accessToken;

    // Unique identifier of the authenticated user
    private Long userId;

    // Name of the authenticated user
    private String name;

    // Email address of the authenticated user
    private String email;

    // Role of the authenticated user
    private String role;
}
