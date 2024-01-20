package com.example.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class DelayService {
    private static final Long MAX_DELAY_SECONDS = 60L;

    public long calculateDelay(Integer attempt) {
        final double pow = Math.pow(2, attempt);
        return (long) Math.min(pow, MAX_DELAY_SECONDS);
    }
}
