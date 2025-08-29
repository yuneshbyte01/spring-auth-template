package com.example.authtemplate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo controller for testing secured endpoints.
 *
 * <p>Provides a sample endpoint to verify:</p>
 * <ul>
 *   <li>JWT authentication</li>
 *   <li>Role-based access control (ROLE_USER or ROLE_ADMIN)</li>
 * </ul>
 */
@RestController
public class DemoController {

    // Protected demo endpoint requiring valid JWT
    @Operation(
            summary = "Hello endpoint",
            description = "A protected endpoint. Requires JWT and ROLE_USER or ROLE_ADMIN."
    )
    @ApiResponse(responseCode = "200", description = "Successfully accessed the protected endpoint")
    @ApiResponse(responseCode = "403", description = "Forbidden - missing or invalid JWT")
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, secured world! 🎉 You have a valid JWT.";
    }
}
