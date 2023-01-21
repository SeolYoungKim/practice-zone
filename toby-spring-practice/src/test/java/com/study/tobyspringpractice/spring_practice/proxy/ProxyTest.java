package com.study.tobyspringpractice.spring_practice.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;


@SpringBootTest
public class ProxyTest {
    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    void targetTest() {
        final Hello hello = new HelloTarget();

        assertThat(hello.sayHello("kim")).isEqualTo("Hello kim");
        assertThat(hello.sayHi("kim")).isEqualTo("Hi kim");
        assertThat(hello.sayThankYou("kim")).isEqualTo("Thank you kim");
    }

    @Test
    void decoratorPatternProxyTest() {
        final Hello hello = new HelloUppercase(new HelloTarget());

        assertThat(hello.sayHello("kim")).isEqualTo("HELLO KIM");
        assertThat(hello.sayHi("kim")).isEqualTo("HI KIM");
        assertThat(hello.sayThankYou("kim")).isEqualTo("THANK YOU KIM");
    }

    @Test
    void dynamicProxyTest() {
        final Hello proxy = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),  // TODO 동적으로 생성되는 다이나믹 프록시 클래스의 로딩에 사용할 클래스 로더
                new Class[]{Hello.class},  // 구현할 인터페이스
                new UppercaseHandler(new HelloTarget()));

        assertThat(proxy.sayHello("kim")).isEqualTo("HELLO KIM");
        assertThat(proxy.sayHi("kim")).isEqualTo("HI KIM");
        assertThat(proxy.sayThankYou("kim")).isEqualTo("THANK YOU KIM");
    }

    @Test
    void transactionHandlerTest() {
        final Hello proxy = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new TransactionHandler(
                        new HelloTarget(),
                        transactionManager,
                        new String[]{"say*"}
                )
        );

        assertThat(proxy.sayHello("kim")).isEqualTo("Hello kim");
        assertThat(proxy.sayHi("kim")).isEqualTo("Hi kim");
        assertThat(proxy.sayThankYou("kim")).isEqualTo("Thank you kim");
    }

    @Test
    void proxyFactoryBeanTest() {
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());  // 타깃 오브젝트의 인터페이스를 "자동 검출 기능"을 사용해 타깃 오브젝트가 구현하고 있는 인터페이스 정보를 알아냄. -> 인터페이스를 모두 구현하는 프록시 생성
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        final Hello proxy = (Hello) proxyFactoryBean.getObject();
        assertThat(proxy.sayHello("kim")).isEqualTo("HELLO KIM");
        assertThat(proxy.sayHi("kim")).isEqualTo("HI KIM");
        assertThat(proxy.sayThankYou("kim")).isEqualTo("THANK YOU KIM");
    }

    @Test
    void proxyFactoryBeanPointcutTest() {
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        final Hello proxy = (Hello) proxyFactoryBean.getObject();
        assertThat(proxy.sayHello("kim")).isEqualTo("HELLO KIM");
        assertThat(proxy.sayHi("kim")).isEqualTo("HI KIM");
        assertThat(proxy.sayThankYou("kim")).isEqualTo("Thank you kim");
    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {  // 템플릿/콜백
            final String result = (String) invocation.proceed();  // 해당 메서드를 실행하면 타깃 오브젝트의 메서드를 내부적으로 실행해줌
            return result.toUpperCase();
        }
    }

}
