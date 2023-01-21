package com.study.tobyspringpractice.spring_practice.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 다이나믹 프록시
 * - 해당 프록시가 받는 모든 요청은 invoke() 메서드로 전달된다.
 * - 다이나믹 프록시를 통해 요청이 전달되면 -> 리플렉션 API를 이용해 타깃 오브젝트의 메소드를 호출함
 */
public class UppercaseHandler implements InvocationHandler {
    private final Object target;

    public UppercaseHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args)
            throws Throwable {
        final Object result = method.invoke(target, args);
        if (result instanceof String && method.getName().startsWith("say")) {
            return ((String)result).toUpperCase();
        }

        return result;
    }
}
