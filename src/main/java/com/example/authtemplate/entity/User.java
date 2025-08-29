package com.example.authtemplate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Entity representing a system user.
 *
 * <p>This entity stores authentication and authorization details:
 * <ul>
 *   <li>Unique identifier (id)</li>
 *   <li>User's personal information (name, email)</li>
 *   <li>Security credentials (password, enabled status)</li>
 *   <li>Authorization role (STUDENT, ORGANIZATION, ADMIN, etc.)</li>
 * </ul>
 *
 * <p>Each user has exactly one role and an active status flag
 * to control login availability.</p>
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password") // avoid exposing sensitive info
public class User {

    // Primary key (auto-generated)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the user
    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    // Unique email address of the user
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    // Hashed password for authentication
    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

    // Flag indicating if the account is active
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    // Role assigned to the user (e.g., STUDENT, ORGANIZATION, ADMIN)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
}
