package com.study.tobyspringpractice.template_callback;

@FunctionalInterface
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
