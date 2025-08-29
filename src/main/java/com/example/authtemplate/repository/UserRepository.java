package com.example.authtemplate.repository;

import com.example.authtemplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entity.
 *
 * <p>Provides built-in CRUD operations through {@link JpaRepository}
 * and custom query methods for authentication use cases.</p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email
    Optional<User> findByEmail(String email);

    // Check if a user with the given email exists
    boolean existsByEmail(String email);
}
