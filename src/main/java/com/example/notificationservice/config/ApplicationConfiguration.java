package com.example.notificationservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Objects;

@Configuration(proxyBeanMethods = false)
@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    public RestTemplate brevoApiRestTemplate(ApplicationProperties applicationProperties) {
        var apiKey = Objects.requireNonNull(applicationProperties.getBrevoApi().getApiKey());

        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofSeconds(30))
                .setConnectTimeout(Duration.ofSeconds(30))
                .defaultHeader("api-key", apiKey)
                .requestFactory(ClientHttpRequestFactory.class)
                .build();
    }
}
