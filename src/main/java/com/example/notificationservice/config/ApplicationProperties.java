package com.example.notificationservice.config;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties( prefix = "application" )
public class ApplicationProperties {

    @ConstructorBinding
    public ApplicationProperties(@NotNull Api brevoApi) {
        this.brevoApi = brevoApi;
    }

    @NotNull
    private Api brevoApi;

    @Validated
    @Data
    public static class Api
    {
        @NotNull
        private String url;
        @NotNull
        private String apiKey;
        @NotNull
        private Long defaultTemplateId;
    }
}

