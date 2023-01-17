package com.study.tobyspringpractice.transcation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalTest {
    static ThreadLocal<Integer> LOCAL = new ThreadLocal<>();

    @Test
    void name() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        LOCAL.set(Integer.MAX_VALUE);
        Integer integer = LOCAL.get();

        for (int i = 0; i < 100; i++) {
            int num = i;
            executorService.submit(() -> {
                if (LOCAL.get() == null) {
                    LOCAL.set(num);
                }
                log.info("Local={}", LOCAL.get());
            });
        }
    }
}
