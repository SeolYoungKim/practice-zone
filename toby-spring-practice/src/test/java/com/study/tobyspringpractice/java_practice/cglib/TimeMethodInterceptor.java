package com.study.tobyspringpractice.java_practice.cglib;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {
    private final Object target;

    public TimeMethodInterceptor(final Object target) {
        this.target = target;
    }

    /**
     * obj : CGLIB이 적용된 객체 (this, Enhanced 객체라고 함)
     * method : 호출된 메서드
     * args : 메서드 호출 시 전달된 인수
     * proxy : 메서드 호출에 사용
     */
    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args,
            final MethodProxy methodProxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = methodProxy.invoke(target, args);  // 매뉴얼에 MethodProxy가 더빠르다고 나와있음

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);

        return result;
    }
}
