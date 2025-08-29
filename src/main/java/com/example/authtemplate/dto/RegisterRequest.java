package com.example.authtemplate.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration requests.
 *
 * <p>The client uses this object to send user details
 * when creating a new account.</p>
 */
@Data
public class RegisterRequest {

    // Full name of the user
    private String name;

    // Email address of the user
    private String email;

    // Password chosen by the user
    private String password;
}
