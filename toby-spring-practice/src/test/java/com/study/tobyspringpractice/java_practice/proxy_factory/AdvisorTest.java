package com.study.tobyspringpractice.java_practice.proxy_factory;

import com.study.tobyspringpractice.java_practice.cglib.ServiceImpl;
import com.study.tobyspringpractice.java_practice.cglib.ServiceInterface;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Slf4j
public class AdvisorTest {
    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }

    @DisplayName("직접 만든 포인트컷")
    @Test
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        log.info("--------------구분선--------------");
        proxy.find();
    }

    @DisplayName("스프링이 제공하는 포인트컷")
    @Test
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        log.info("--------------구분선--------------");
        proxy.find();
    }

    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    @Slf4j
    static class MyMethodMatcher implements MethodMatcher {
        private static final String MATCH_NAME = "save";

        @Override
        public boolean matches(final Method method, final Class<?> targetClass) {  // 정적인 정보 -> 캐싱 가능
            log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
            boolean result = method.getName().equals(MATCH_NAME);
            log.info("포인트컷 결과 result={}", result);
            return result;
        }

        // isRuntime이 참이면
        @Override
        public boolean isRuntime() {
            return false;  // false인 경우 클래스의 정적인 정보만 사용 -> 스프링 내부에서 캐싱을 통한 성능 향상 가능
            // 하지만, true인 경우에는 매개변수가 동적으로 변경된다고 판단하여 캐싱을 하지 않음.
        }

        // 위의 matches가 아닌, 아래의 matches가 수행된다.
        @Override
        public boolean matches(final Method method, final Class<?> targetClass,
                final Object... args) {  // 내부 인수들이 넘어옴 -> 캐싱 불가능
            return false;
        }
    }
}
