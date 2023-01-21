package com.study.tobyspringpractice.java_practice.jdk_dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class TimeInvocationFilterHandler implements InvocationHandler {
    private final Object target;  // 진짜 객체
    private final String[] patterns;

    public TimeInvocationFilterHandler(final Object target, final String[] patterns) {
        this.target = target;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        String methodName = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {  // 패턴이 맞지 않으면
            return method.invoke(target, args);  // 실제 메서드만 호출해주고 끝낸다.
        }

        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();


        Object result = method.invoke(target, args); // target : 호출 대상 , args : 호출 시 넘겨줄 인수

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);

        return result;
    }
}
