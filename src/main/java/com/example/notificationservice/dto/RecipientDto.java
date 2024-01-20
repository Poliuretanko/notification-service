package com.example.notificationservice.dto;

import com.example.notificationservice.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipientDto {
    private String email;
    private String name;

    public static RecipientDto from(Recipient recipient) {
        return new RecipientDto(recipient.getEmail(), recipient.getName());
    }
}
