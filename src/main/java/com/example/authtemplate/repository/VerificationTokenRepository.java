package com.example.authtemplate.repository;

import com.example.authtemplate.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link VerificationToken} entity.
 *
 * <p>Provides methods for:</p>
 * <ul>
 *   <li>Finding a token by its string value</li>
 *   <li>Deleting a token by its string value</li>
 * </ul>
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    // Find verification token by token string
    Optional<VerificationToken> findByToken(String token);

    // Delete verification token by token string
    void deleteByToken(String token);
}
