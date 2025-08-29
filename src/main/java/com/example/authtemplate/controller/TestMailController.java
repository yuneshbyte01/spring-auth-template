package com.example.authtemplate.controller;

import com.example.authtemplate.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for testing email sending independently of registration flow.
 */
@RestController
public class TestMailController {

    private final EmailService emailService;

    public TestMailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Send a test email.
     * Example: GET <a href="http://localhost:8080/api/test-mail?to=someone@gmail.com">...</a>
     *
     * @param to recipient email
     * @return status message
     */
    @GetMapping("/api/test-mail")
    public ResponseEntity<String> sendTestMail(@RequestParam String to) {
        try {
            emailService.sendEmail(
                    to,
                    "✅ Test Email from Spring Boot",
                    "Hello! This is a test email from your Spring Boot Auth Template."
            );
            return ResponseEntity.ok("✅ Test email sent successfully to " + to);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("❌ Failed to send email: " + e.getMessage());
        }
    }
}
