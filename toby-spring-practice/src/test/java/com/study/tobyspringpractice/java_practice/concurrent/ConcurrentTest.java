package com.study.tobyspringpractice.java_practice.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConcurrentTest {
    static class TestNone {
        static int i = 0, j = 0;

        static void one() {
            i++; j++;
        }

        static void two() {
            if (i != j) {
                System.out.println("i=" + i + " j=" + j);
            }
        }
    }

    static class TestSynchronized {
        static int i = 0, j = 0;

        static synchronized void one() {
            i++; j++;
        }

        static synchronized void two() {
            if (i != j) {
                System.out.println("i=" + i + " j=" + j);
            }
        }
    }

    static class TestVolatile {
        static volatile int i = 0, j = 0;

        static void one() {
            i++; j++;
        }

        static void two() {
            if (i != j) {
                System.out.println("i=" + i + " j=" + j);
            }
        }

    }

    @DisplayName("동기화 처리를 하지 않은 클래스 테스트")
    @Test
    void threadTest() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                TestNone.one();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                TestNone.two();
            }
        });

        thread1.start();
        thread2.start();
    }

    @DisplayName("동기화 처리 테스트(synchronized)")
    @Test
    void threadTest2() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                TestSynchronized.one();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                TestSynchronized.two();
            }
        });

        thread1.start();
        thread2.start();
    }

    @DisplayName("동기화 처리 테스트(volatile)")
    @Test
    void threadTest3() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                TestVolatile.one();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                TestVolatile.two();
            }
        });

        thread1.start();
        thread2.start();
    }
}
