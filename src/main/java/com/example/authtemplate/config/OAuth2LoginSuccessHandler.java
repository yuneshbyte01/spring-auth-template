package com.example.authtemplate.config;

import com.example.authtemplate.entity.Role;
import com.example.authtemplate.entity.User;
import com.example.authtemplate.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Success handler for OAuth2 login.
 *
 * <p>Executed after a successful OAuth2 authentication. It:</p>
 * <ul>
 *   <li>Extracts user details from the OAuth2 provider</li>
 *   <li>Registers the user in the database if not already present</li>
 *   <li>Generates a JWT token for the authenticated user</li>
 *   <li>Returns the token and basic user details as a JSON response</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    // Repository for user persistence
    private final UserRepository userRepository;

    // Utility for generating JWT tokens
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Extract user details from the OAuth2 provider
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Get email (fallback for GitHub where email may be hidden)
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            email = oAuth2User.getAttribute("login") + "@github.local";
        }

        // Get name (fallback to login if not provided)
        String name = oAuth2User.getAttribute("name");
        if (name == null) {
            name = oAuth2User.getAttribute("login");
        }

        // Create a user in DB if not exists
        String finalName = name;
        String finalEmail = email;
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .name(finalName)
                    .email(finalEmail)
                    .password("OAUTH_USER") // placeholder password
                    .enabled(true)
                    .role(Role.ROLE_USER)
                    .build();
            return userRepository.save(newUser);
        });

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // Write JSON response
        response.setContentType("application/json");
        response.getWriter().write(String.format(
                "{ \"accessToken\": \"%s\", \"userId\": %d, \"email\": \"%s\", \"role\": \"%s\" }",
                token, user.getId(), user.getEmail(), user.getRole().name()
        ));
    }
}
