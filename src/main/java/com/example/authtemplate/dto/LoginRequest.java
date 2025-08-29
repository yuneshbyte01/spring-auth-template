package com.example.authtemplate.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for login requests.
 *
 * <p>The client uses this object to send user credentials
 * (email and password) for authentication.</p>
 */
@Data
public class LoginRequest {

    // Email address of the user
    private String email;

    // Password of the user
    private String password;
}
