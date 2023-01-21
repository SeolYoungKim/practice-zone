package com.study.tobyspringpractice.java_practice;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ClassLoaderTest {
    @Test
    void classInitTest() throws Exception {
//        Class<?> classDriverA = Class.forName("com.study.java_practice.ClassLoaderTest$DriverA");

        // Class<T> 인스턴스가 생성이 안되는 것 같음
        ClassLoader classLoader = DriverB.class.getClassLoader();
        Class<?> classDriverB = classLoader.loadClass("com.study.java_practice.ClassLoaderTest$DriverB");
        log.info(Arrays.toString(classDriverB.getDeclaredConstructors()));
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

    static class DriverC {

    }
}
