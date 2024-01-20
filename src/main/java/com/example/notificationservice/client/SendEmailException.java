package com.example.notificationservice.client;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SendEmailException extends Exception {
    static final String DEFAULT_MESSAGE = "Can't send an email to the mailing service";

    private String message;

    public SendEmailException() {
        message = DEFAULT_MESSAGE;
    }
}
