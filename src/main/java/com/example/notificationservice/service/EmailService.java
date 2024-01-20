package com.example.notificationservice.service;

import com.example.notificationservice.client.BrevoClient;
import com.example.notificationservice.domain.Email;
import com.example.notificationservice.domain.EmailStatus;
import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.repository.EmailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Slf4j
@Service
public class EmailService {

    private final BrevoClient brevoClient;
    private final EmailDao emailDao;
    private final DelayService delayService;
    private static final Integer MAX_ATTEMPTS = 5;

    public EmailService(BrevoClient brevoClient, EmailDao emailDao, DelayService delayService) {
        this.brevoClient = brevoClient;
        this.emailDao = emailDao;
        this.delayService = delayService;
    }

    public void saveNotification(EmailDto emailDto) {
        log.info("Saving a email message {}", emailDto);
        var nextAttemptTime = ZonedDateTime.now();
        var email = emailDto.convertToEmail(nextAttemptTime);
        emailDao.addEmail(email);
    }

    public void sendNotifications() {
        var now = ZonedDateTime.now();
        log.info("Collecting emails to send on {}", now);
        var emailsToSend = emailDao.getEmailsForSend(now);
        log.info("Collected emails to send {}", emailsToSend.size());
        var emailsToResend = new ArrayList<Email>();

        emailsToSend.forEach(email -> {
            sendNotification(email);
            if (email.getStatus().equals(EmailStatus.FAILED_TO_SEND)) {
                email.setStatus(EmailStatus.WAITING_FOR_SEND);
                emailsToResend.add(email);
            }
        });

        log.info("Rescheduling failed emails {}", emailsToResend.size());
        emailDao.addEmails(emailsToResend);
    }

    void sendNotification(Email email) {
        var emailDto = EmailDto.from(email);

        try {
            email.increaseAttemptsCount();

            log.info("Sending email {}", emailDto);
            brevoClient.sendEmail(emailDto);

            email.setStatus(EmailStatus.SENT);
        } catch (Exception e) {
            log.info("Failed to send an email {} on {} attempt. The error {}",
                    emailDto, email.getSendingAttempts(), e.getMessage());
            if (email.getSendingAttempts().equals(MAX_ATTEMPTS)) {
                email.setStatus(EmailStatus.ATTEMPTS_ARE_OVER);
                return;
            }

            log.info("Resending an email {}", emailDto);
            var previousAttempt = email.getNextAttempt();
            var delay = delayService.calculateDelay(email.getSendingAttempts());
            var nextAttempt = previousAttempt.plusSeconds(delay);
            email.setNextAttempt(nextAttempt);
            email.setStatus(EmailStatus.FAILED_TO_SEND);
        }
    }
}
