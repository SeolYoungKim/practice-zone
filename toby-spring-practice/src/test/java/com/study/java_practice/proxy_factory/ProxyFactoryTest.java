package com.study.java_practice.proxy_factory;

import static org.assertj.core.api.Assertions.*;

import com.study.java_practice.cglib.ConcreteService;
import com.study.java_practice.cglib.ServiceImpl;
import com.study.java_practice.cglib.ServiceInterface;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {
    @DisplayName("인터페이스가 있을 경우 JDK 동적 프록시를 사용한다.")
    @Test
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();  // JdkDynamicAopProxy로 넘어간다.

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @DisplayName("람다식일 경우 JDK 동적 프록시를 사용한다.")
    @Test
    void lambdaProxy() {
        Consumer<String> target = str -> log.info("{}", str);
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        Consumer<String> proxy = (Consumer<String>) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.accept("람다식도 프록시가 적용됩니다.");

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @DisplayName("인터페이스가 없을 경우 CGLIB 프록시를 사용한다.")
    @Test
    void cglibProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    }

    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB을 사용하고, 클래스 기반의 프록시를 사용하게 된다.")
    @Test
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();  // JdkDynamicAopProxy로 넘어간다.

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @DisplayName("람다식은 CGLIB proxy를 적용할 수 없습니다. 무조건 JDK proxy로 만들어집니다.")  // 람다식은 구현체가 없다??
    @Test
    void proxyTargetClassLambda() {
        Consumer<String> target = str -> log.info("{}", str);
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());

        Consumer<String> proxy = (Consumer<String>) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.accept("람다식 이거 되냐?");

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }
}
