package com.study.tobyspringpractice.spring_practice.proxy;

public class HelloTarget implements Hello {

    @Override
    public String sayHello(final String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(final String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankYou(final String name) {
        return "Thank you " + name;
    }
}
