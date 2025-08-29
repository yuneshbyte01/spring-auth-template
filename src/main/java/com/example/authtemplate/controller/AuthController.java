package com.example.authtemplate.controller;

import com.example.authtemplate.dto.LoginRequest;
import com.example.authtemplate.dto.RegisterRequest;
import com.example.authtemplate.dto.AuthResponse;
import com.example.authtemplate.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints.
 *
 * <p>Provides APIs for:</p>
 * <ul>
 *   <li>User registration</li>
 *   <li>User login (JWT issuance)</li>
 *   <li>Email verification</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Authentication service for handling business logic
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a new user
    @Operation(summary = "Register a new user", description = "Registers a user with email and password")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // Authenticate a user and return a JWT
    @Operation(summary = "Login user", description = "Authenticates a user and returns JWT token")
    @ApiResponse(responseCode = "200", description = "Login successful, returns JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Verify the user account via token
    @Operation(summary = "Verify email", description = "Activates a user account after email verification")
    @ApiResponse(responseCode = "200", description = "Email verified successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        return ResponseEntity.ok(authService.verifyUser(token));
    }

}
