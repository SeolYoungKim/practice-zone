package com.study.tobyspringpractice.spring_practice.proxy;

import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Component
public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
    private final String mappedClassName;
    private final String mappedMethodName;

    public NameMatchClassMethodPointcut(
            @Value("HelloT*") final String mappedClassName,
            @Value("sayH*") final String mappedMethodName) {
        this.mappedClassName = mappedClassName;
        this.setClassFilter(clazz ->
                PatternMatchUtils.simpleMatch(mappedClassName, clazz.getSimpleName()));

        this.mappedMethodName = mappedMethodName;
        this.setMappedName(mappedMethodName);
    }

    public String mappedClassName() {
        return mappedClassName;
    }

    public String mappedMethodName() {
        return mappedMethodName;
    }
}
