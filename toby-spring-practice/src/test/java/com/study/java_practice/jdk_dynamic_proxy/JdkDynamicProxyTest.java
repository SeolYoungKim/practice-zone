package com.study.java_practice.jdk_dynamic_proxy;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class JdkDynamicProxyTest {
    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),
                new Class[]{AInterface.class},
                handler);

        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),
                new Class[]{BInterface.class},
                handler);

        proxy.call();
        proxy.call2();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void filterHandler() {
        BInterface target = new BImpl();
        TimeInvocationFilterHandler handler = new TimeInvocationFilterHandler(target,
                new String[]{"call*"});

        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),
                new Class[]{BInterface.class},
                handler);

        log.info("call* 메서드 호출");
        proxy.call();
        proxy.call2();

        System.out.println("----------------------");
        log.info("call*이 아닌 메서드 호출");
        proxy.methodNotUseProxy();
    }

    @DisplayName("인터페이스가 아닌 구체 클래스로 프록시를 만들 경우 예외가 발생한다.")
    @Test
    void fail() {
        BInterface target = new BImpl();
        TimeInvocationFilterHandler handler = new TimeInvocationFilterHandler(target,
                new String[]{"call*"});

        assertThatThrownBy(() -> Proxy.newProxyInstance(
                BInterface.class.getClassLoader(), new Class[]{BImpl.class}, handler))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("BImpl is not an interface");
    }
}
