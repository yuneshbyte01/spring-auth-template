package com.example.authtemplate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, secured world! 🎉 You have a valid JWT.";
    }
}
