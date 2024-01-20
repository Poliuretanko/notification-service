package com.example.notificationservice.domain;

public enum SendingAttempt {
    ZERO(0, 1000),
    FIRST(1, 2000),
    ;


    private final Integer attempt;
    private final Integer delayInMilliseconds;

    SendingAttempt(Integer attempt, Integer delayInMilliseconds) {
        this.attempt = attempt;
        this.delayInMilliseconds = delayInMilliseconds;
    }
}
