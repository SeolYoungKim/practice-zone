package com.study.java_practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ClassLoaderTest {
    @Test
    void classInitTest() throws Exception {
        Class<?> classDriverA = Class.forName("com.study.java_practice.ClassLoaderTest$DriverA");

        ClassLoader classLoader = classDriverA.getClassLoader();
        Class<?> classDriverB = classLoader.loadClass("com.study.java_practice.ClassLoaderTest$DriverB");
    }

    static class DriverA {
        static {
            log.info("DriverA : 초기화 되었습니다.");
        }
    }

    static class DriverB {
        static {
            log.info("DriverB : 초기화 되었습니다.");
        }
    }
}
