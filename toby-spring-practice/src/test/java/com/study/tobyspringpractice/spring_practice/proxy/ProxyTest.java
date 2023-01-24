package com.study.tobyspringpractice.spring_practice.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
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

    //clazz -> clazz.getName().equals("HelloTarget");
    @Test
    void classNamePointcutAdvisor() {
        final NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return clazz -> clazz.getSimpleName().startsWith("HelloT");
            }
        };

        classMethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget {}
        checkAdviced(new HelloWorld(), classMethodPointcut, false);

        class HelloToby extends HelloTarget {}
        checkAdviced(new HelloToby(), classMethodPointcut, true);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        final Hello proxy = (Hello) proxyFactoryBean.getObject();

        if (adviced) {
            assertThat(proxy.sayHello("kim")).isEqualTo("HELLO KIM");
            assertThat(proxy.sayHi("kim")).isEqualTo("HI KIM");
            assertThat(proxy.sayThankYou("kim")).isEqualTo("Thank you kim");
        } else {
            assertThat(proxy.sayHello("kim")).isEqualTo("Hello kim");
            assertThat(proxy.sayHi("kim")).isEqualTo("Hi kim");
            assertThat(proxy.sayThankYou("kim")).isEqualTo("Thank you kim");
        }
    }

    @Autowired
    private DefaultPointcutAdvisor defaultPointcutAdvisor;

    @DisplayName("DI 확인")
    @Test
    void proxyComponentTest() {
        final Pointcut pointcut = defaultPointcutAdvisor.getPointcut();
        final MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        final ClassFilter classFilter = pointcut.getClassFilter();

        final String methodMatcherSimpleName = methodMatcher.getClass().getSimpleName();
        assertThat(methodMatcherSimpleName).isEqualTo("NameMatchClassMethodPointcut");

        final String classFilterSimpleName = classFilter.getClass().getSimpleName();
        assertThat(classFilterSimpleName).startsWith("NameMatchClassMethodPointcut");

        final Advice advice = defaultPointcutAdvisor.getAdvice();
        final String adviceSimpleName = advice.getClass().getSimpleName();
        assertThat(adviceSimpleName).isEqualTo("TransactionAdvice");
    }

    @DisplayName("classFilter에 구현한 matches()함수 작동 확인")
    @Test
    void classFilterMatchesTest() {
        final Pointcut pointcut = defaultPointcutAdvisor.getPointcut();
        final ClassFilter classFilter = pointcut.getClassFilter();

        assertThat(classFilter.matches(HelloTarget.class)).isTrue();

        class HelloToby extends HelloTarget {}
        assertThat(classFilter.matches(HelloToby.class)).isTrue();

        class HelloWorld extends HelloTarget {}
        assertThat(classFilter.matches(HelloWorld.class)).isFalse();
    }

    @Autowired
    private HelloTarget helloTarget;

    @Test
    void helloTargetIsProxied() {
        System.out.println(helloTarget.sayHello("kim"));
        System.out.println(helloTarget.sayHi("kim"));
        System.out.println(helloTarget.sayThankYou("kim"));
    }

    @DisplayName("스프링 빈의 메서드들은 전부 프록시 객체의 메서드다 (CGLIB)")
    @Test
    void test() {
        final Method[] declaredMethods = helloTarget.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            final String method = declaredMethod.toString();
            assertThat(method).contains("HelloTarget$$SpringCGLIB$$");
        }
    }
}
