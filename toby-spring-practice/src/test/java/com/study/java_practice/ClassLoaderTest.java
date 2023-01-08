package com.study.java_practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ClassLoaderTest {

    @Test
    void name() throws Exception {
        Class<?> classHello = Class.forName("com.study.java_practice.ReflectionTest$Hello");
        ClassLoader classLoader = classHello.getClassLoader();
        Class<?> aClass = classLoader.loadClass("com.study.java_practice.ReflectionTest$Hello");
        log.info("classLoader = {}", classLoader);
        log.info("classLoader = {}", classLoader.getParent());
        log.info("classLoader = {}", classLoader.getParent().getParent());
    }

    static class DriverA {
        static {
            System.out.println("DriverA - 초기화 되었습니다.");
        }
    }

    static class DriverB {
        static {
            System.out.println("DriverB - 초기화 되었습니다.");
        }
    }
}
