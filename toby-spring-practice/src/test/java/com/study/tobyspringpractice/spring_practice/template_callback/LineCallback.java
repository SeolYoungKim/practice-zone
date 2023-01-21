package com.study.tobyspringpractice.spring_practice.template_callback;

@FunctionalInterface
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
