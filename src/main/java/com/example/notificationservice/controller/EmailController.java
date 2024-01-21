package com.example.notificationservice.controller;

import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.queue.producer.EmailNotificationProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailNotificationProducer emailNotificationProducer;

    public EmailController(EmailNotificationProducer emailNotificationProducer) {
        this.emailNotificationProducer = emailNotificationProducer;
    }

    /**
     * Sends an email dto to the SQS query
     */
    @PostMapping("/emails")
    public void createEmailNotification(@RequestBody EmailDto emailDto) {
        emailNotificationProducer.send(emailDto);
    }
}
