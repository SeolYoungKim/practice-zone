package com.study.java_practice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        // 공통 로직 2 종료
    }

    @Test
    void reflection1()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보 획득
        Class<?> classHello = Class.forName("com.study.java_practice.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA
        Method callA = classHello.getMethod("callA");  // 추상화!
        Object result1 = callA.invoke(target);
        log.info("result1={}", result1);

        // callB
        Method callB = classHello.getMethod("callB");
        Object result2 = callB.invoke(target);
        log.info("result2={}", result2);

    }

    @Test
    void reflection2() throws Exception{
        Class<?> classHello = Class.forName("com.study.java_practice.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA
        Method callA = classHello.getMethod("callA");  // 추상화!
        dynamicCall(callA, target);

        // callB
        Method callB = classHello.getMethod("callB");
        dynamicCall(callB, target);
    }


    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
