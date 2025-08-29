package com.example.authtemplate.service;

import com.example.authtemplate.config.JwtUtil;
import com.example.authtemplate.dto.AuthResponse;
import com.example.authtemplate.dto.LoginRequest;
import com.example.authtemplate.dto.RegisterRequest;
import com.example.authtemplate.entity.PasswordResetToken;
import com.example.authtemplate.entity.Role;
import com.example.authtemplate.entity.User;
import com.example.authtemplate.entity.VerificationToken;
import com.example.authtemplate.exception.InvalidCredentialsException;
import com.example.authtemplate.exception.UserAlreadyExistsException;
import com.example.authtemplate.exception.UserNotFoundException;
import com.example.authtemplate.repository.PasswordResetTokenRepository;
import com.example.authtemplate.repository.UserRepository;
import com.example.authtemplate.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service layer for handling authentication operations.
 *
 * <p>Provides functionality for:</p>
 * <ul>
 *   <li>Registering new users with email verification</li>
 *   <li>Authenticating users with email and password</li>
 *   <li>Blocking login until email is verified</li>
 *   <li>Issuing JWT tokens upon successful authentication</li>
 * </ul>
 */
@Service
@AllArgsConstructor
public class AuthService {

    // Repository for accessing and persisting user data
    private final UserRepository userRepository;

    // Repository for verification token storage
    private final VerificationTokenRepository verificationTokenRepository;

    // Repository for password-reset tokens
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    // Password encoder for hashing and verifying passwords
    private final PasswordEncoder passwordEncoder;

    // Utility for generating and validating JWT tokens
    private final JwtUtil jwtUtil;

    // Service for sending emails
    private final EmailService emailService;

    // Register a new user (inactive until email verification)
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        // Save user (disabled by default)
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        // Save a verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .build();
        verificationTokenRepository.save(verificationToken);

        // ---- Transaction ends here if the commit succeeds ----

        // Send email outside transaction
        try {
            String link = "http://localhost:8080/api/auth/verify?token=" + token;
            emailService.sendEmail(
                    user.getEmail(),
                    "Verify your account",
                    "Hello " + user.getName() + ",\n\n" +
                            "Please verify your account by clicking the link below:\n" +
                            link + "\n\nThis link will expire in 24 hours."
            );
        } catch (Exception e) {
            System.err.println("⚠️ Failed to send email: " + e.getMessage());
            // Optionally log to monitoring but do NOT roll back
        }
    }

    // Authenticate user and return JWT with user details
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        // Block login if isn't verified
        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Please verify your email before logging in");
        }

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

    // Verify a user's email using the token
    @Transactional
    public String verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("❌ Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidCredentialsException("❌ Verification token expired");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true); // activate an account
        userRepository.save(user);

        // Clean up token so it can't be reused
        verificationTokenRepository.delete(verificationToken);

        return "✅ Email verified successfully! You can now log in.";
    }

    // Handle forgot password request
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        // Generate token
        String token = UUID.randomUUID().toString();

        // Save reset token
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30)) // valid 30 minutes
                .build();

        passwordResetTokenRepository.save(resetToken);

        // Send reset email
        try {
            String link = "http://localhost:8080/reset-password.html?token=" + token;
            emailService.sendEmail(
                    user.getEmail(),
                    "Password Reset Request",
                    "Hello " + user.getName() + ",\n\n" +
                            "Click the link below to reset your password:\n" +
                            link + "\n\nThis link will expire in 30 minutes."
            );
        } catch (Exception e) {
            System.err.println("⚠️ Failed to send password reset email: " + e.getMessage());
        }
    }

    // Reset password using the token
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid reset token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidCredentialsException("Reset token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete token after successful reset
        passwordResetTokenRepository.delete(resetToken);
    }

}
