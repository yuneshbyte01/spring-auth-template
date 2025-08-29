package com.example.authtemplate.service;

import com.example.authtemplate.config.JwtUtil;
import com.example.authtemplate.dto.AuthResponse;
import com.example.authtemplate.dto.LoginRequest;
import com.example.authtemplate.dto.RegisterRequest;
import com.example.authtemplate.entity.Role;
import com.example.authtemplate.entity.User;
import com.example.authtemplate.exception.InvalidCredentialsException;
import com.example.authtemplate.exception.UserAlreadyExistsException;
import com.example.authtemplate.exception.UserNotFoundException;
import com.example.authtemplate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling authentication operations.
 *
 * <p>Provides functionality for:</p>
 * <ul>
 *   <li>Registering new users</li>
 *   <li>Authenticating users with email and password</li>
 *   <li>Issuing JWT tokens upon successful authentication</li>
 * </ul>
 */
@Service
@AllArgsConstructor
public class AuthService {

    // Repository for accessing and persisting user data
    private final UserRepository userRepository;

    // Password encoder for hashing and verifying passwords
    private final PasswordEncoder passwordEncoder;

    // Utility for generating and validating JWT tokens
    private final JwtUtil jwtUtil;

    // Register a new user with default role USER
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // store hashed password
                .enabled(true)
                .role(Role.ROLE_USER) // assign default role
                .build();

        userRepository.save(user);
    }

    // Authenticate user and return JWT with user details
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password for email: " + request.getEmail());
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
