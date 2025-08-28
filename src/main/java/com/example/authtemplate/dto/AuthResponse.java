package com.example.authtemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private Long userId;
    private String name;
    private String email;
    private String role;
}


