package com.example.authtemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * REST controller for user endpoints.
 *
 * <p>Provides APIs that are accessible by both USER and ADMIN roles.</p>
 */
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    /**
     * User profile endpoint.
     *
     * @return Welcome message for user profile access
     */
    @GetMapping("/profile")
    @Operation(
        summary = "User Profile", 
        description = "Access user profile (USER or ADMIN role required)",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "User profile accessed successfully")
    @ApiResponse(responseCode = "403", description = "Access denied - authentication required")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Welcome User! 👋\n\nYou have access to your user profile.");
    }
}
