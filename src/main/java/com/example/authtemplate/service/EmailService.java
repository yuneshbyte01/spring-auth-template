package com.example.authtemplate.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending email notifications.
 *
 * <p>Used to send:</p>
 * <ul>
 *   <li>Email verification links for account activation</li>
 *   <li>Other authentication-related notifications</li>
 * </ul>
 */
@Service
public class EmailService {

    // Spring's mail sender for sending emails
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Send a simple text email
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
