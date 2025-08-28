package com.example.authtemplate.config;

import com.example.authtemplate.entity.Role;
import com.example.authtemplate.entity.User;
import com.example.authtemplate.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            // fallback if email hidden
            email = oAuth2User.getAttribute("login") + "@github.local";
        }

        String name = oAuth2User.getAttribute("name");
        if (name == null) {
            name = oAuth2User.getAttribute("login");
        }

        String finalName = name;
        String finalEmail = email;
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .name(finalName)
                    .email(finalEmail)
                    .password("OAUTH_USER") // fake password
                    .enabled(true)
                    .role(Role.ROLE_USER)
                    .build();
            return userRepository.save(newUser);
        });

        String token = jwtUtil.generateToken(user.getEmail());

        response.setContentType("application/json");
        response.getWriter().write(
                "{ \"accessToken\": \"" + token + "\", " +
                        "\"userId\": " + user.getId() + ", " +
                        "\"email\": \"" + user.getEmail() + "\", " +
                        "\"role\": \"" + user.getRole().name() + "\" }"
        );
    }

}
