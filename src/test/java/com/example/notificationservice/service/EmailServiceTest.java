package com.example.notificationservice.service;

import com.example.notificationservice.client.BrevoClient;
import com.example.notificationservice.client.SendEmailException;
import com.example.notificationservice.domain.Email;
import com.example.notificationservice.domain.Recipient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @MockBean
    private BrevoClient brevoClient;

    @Test
    void testSend() throws SendEmailException {
        var email = createEmail();

        emailService.sendNotification(email);

        verify(brevoClient).sendEmail(any());
    }

    private Email createEmail() {
        var recipients = Set.of(new Recipient("denis@gorbatykh.de", "Denis Gorbatykh"));
        var now = ZonedDateTime.now();
        return new Email(1L, recipients, null, now);
    }
}
