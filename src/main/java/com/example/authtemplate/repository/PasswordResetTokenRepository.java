package com.example.authtemplate.repository;

import com.example.authtemplate.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link PasswordResetToken}.
 *
 * <p>Provides methods to:</p>
 * <ul>
 *   <li>Find a reset token by its string value</li>
 *   <li>Delete a token once used</li>
 * </ul>
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    // Find reset token by token string
    Optional<PasswordResetToken> findByToken(String token);

    // Delete token by string
    void deleteByToken(String token);
}
