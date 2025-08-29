package com.example.authtemplate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing an email verification token.
 *
 * <p>Each token record contains:</p>
 * <ul>
 *   <li>Unique token string</li>
 *   <li>Associated user</li>
 *   <li>Expiry date-time (after which token is invalid)</li>
 * </ul>
 */
@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationToken {

    // Primary key (auto-generated)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique token string
    @Column(nullable = false, unique = true)
    private String token;

    // Associated user (one-to-one relationship)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Expiration date and time of the token
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
