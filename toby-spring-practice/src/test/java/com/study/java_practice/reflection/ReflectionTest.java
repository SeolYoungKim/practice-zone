package com.study.java_practice.reflection;

import static org.assertj.core.api.Assertions.*;

import com.study.tobyspringpractice.learning_test.JUnitTest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {
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
        Class<?> classHello = Class.forName("com.study.java_practice.reflection.ReflectionTest$Hello");

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
        Class<?> classHello = Class.forName("com.study.java_practice.reflection.ReflectionTest$Hello");

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

    @DisplayName("메소드의 클래스 이름을 꺼내온다.")
    @Test
    void methodName() throws Exception {
        final String className = "Hello";
        Class<?> classHello = Class.forName("com.study.java_practice.reflection.ReflectionTest$" + className);

        final String methodName = "callA";
        Method callA = classHello.getMethod(methodName);

        assertThat(callA.getDeclaringClass().getSimpleName()).isEqualTo(className);
        assertThat(callA.getName()).isEqualTo(methodName);
    }

    @DisplayName("인터페이스의 클래스 로더와 구현체의 클래스 로더는 같다.")
    @Test
    void differentTest() {
        ClassLoader interfaceClassLoader = TestInterface.class.getClassLoader();
        ClassLoader implClassLoader = TestImpl.class.getClassLoader();

        log.info("ClassLoaderByInterface : {}", interfaceClassLoader);
        log.info("ClassLoaderByImplClass : {}", implClassLoader);

        assertThat(interfaceClassLoader).isSameAs(implClassLoader);
    }
}
