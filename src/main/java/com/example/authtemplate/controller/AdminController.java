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
 * REST controller for admin-only endpoints.
 *
 * <p>Provides APIs that are only accessible by users with ADMIN role.</p>
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    /**
     * Admin dashboard endpoint.
     *
     * @return Welcome message for admin users
     */
    @GetMapping("/dashboard")
    @Operation(
        summary = "Admin Dashboard", 
        description = "Access admin dashboard (ADMIN role required)",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Admin dashboard accessed successfully")
    @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Welcome Admin! 🎉\n\nYou have access to the admin dashboard with full system privileges.");
    }
}
