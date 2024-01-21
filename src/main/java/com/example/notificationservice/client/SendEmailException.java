package com.example.notificationservice.client;

public class SendEmailException extends Exception {
    static final String DEFAULT_MESSAGE = "Can't send an email to the mailing service";

    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException() {
        super(DEFAULT_MESSAGE);
    }
}
