package com.example.notificationservice.consumer;

import com.example.notificationservice.config.ApplicationProperties;
import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.service.EmailService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Slf4j
@Service
public class EmailNotificationConsumer {

    private final EmailService emailService;

    public EmailNotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @SqsListener(value = "testBrevoQ")
    public void receiveMessage(Message<EmailDto> message) {
        var emailDto = message.getPayload();
        log.info("Message received from queue {}", message);

        emailService.saveNotification(emailDto);
    }
}
