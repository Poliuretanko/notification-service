package com.example.notificationservice.client;


import com.example.notificationservice.config.ApplicationProperties;
import com.example.notificationservice.dto.EmailDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class BrevoClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public BrevoClient(ApplicationProperties applicationProperties, RestTemplate brevoApiRestTemplate) {
        this.baseUrl = applicationProperties.getBrevoApi().getUrl();
        this.restTemplate = brevoApiRestTemplate;
    }

    public SendEmailResponse sendEmail(EmailDto emailDto) throws SendEmailException {
        var request = SendEmailRequest.from(emailDto);

        var responseEntity = restTemplate.postForEntity(baseUrl + "v3/smtp/email", request, SendEmailResponse.class);
        var responseOptional = Optional.ofNullable(responseEntity.getBody());

        if (responseEntity.getStatusCode().isError()) {
            if (responseOptional.isPresent()) {
                throw new SendEmailException(responseOptional.get().getMessage());
            }
            throw new SendEmailException();
        }
        if (responseOptional.isEmpty()) {
            throw new SendEmailException();
        }

        return responseOptional.get();
    }
}
