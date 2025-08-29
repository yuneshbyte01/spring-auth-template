package com.example.authtemplate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing a password-reset token.
 *
 * <p>Used when a user requests to reset their password.</p>
 * <ul>
 *   <li>Stores a unique token string</li>
 *   <li>Links to the associated user</li>
 *   <li>Contains an expiry date for security</li>
 * </ul>
 */
@Entity
@Table(name = "password_reset_tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique reset token string
    @Column(nullable = false, unique = true)
    private String token;

    // Associated user
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    // Expiry date for the token
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
