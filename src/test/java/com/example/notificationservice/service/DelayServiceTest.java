package com.example.notificationservice.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DelayServiceTest {
    @Autowired
    private DelayService delayService;

    static Stream<Arguments> dataCalculateDelay() {
        return Stream.of(
                Arguments.of(0, 1L),
                Arguments.of(1, 2L),
                Arguments.of(2, 4L),
                Arguments.of(3, 8L),
                Arguments.of(4, 16L),
                Arguments.of(5, 32L),
                Arguments.of(6, 60L),
                Arguments.of(7, 60L)
        );
    }

    @ParameterizedTest
    @MethodSource("dataCalculateDelay")
    void testCalculateDelay(Integer attempt, Long expectedDelay) {
        var actualDelay = delayService.calculateDelay(attempt);
        assertEquals(actualDelay, expectedDelay);
    }
}
