package com.example.notificationservice.controller;

import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/emails")
    public void createEmail(@RequestBody EmailDto emailDto) {
        emailService.saveNotification(emailDto);
    }
}
