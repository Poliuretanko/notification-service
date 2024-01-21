package com.example.notificationservice.scheduler;

import com.example.notificationservice.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private final EmailService emailService;

    public EmailSender(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 1)
    public void sendEmails() {
        emailService.sendNotifications();
    }
}
