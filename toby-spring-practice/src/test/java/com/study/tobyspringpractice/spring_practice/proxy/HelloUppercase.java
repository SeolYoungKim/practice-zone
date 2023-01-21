package com.study.tobyspringpractice.spring_practice.proxy;

/**
 * 데코레이터 패턴 적용
 * [ 문제점 ]
 * 1. 인터페이스의 모든 메소드를 구현해 위임하도록 코드를 만들어야 함
 * 2. 부가 기능인 리턴 값을 대문자로 바꾸는 기능이 모든 메소드에 중복돼서 나타남
 */
public class HelloUppercase implements Hello {
    private final Hello hello;

    public HelloUppercase(final Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(final String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(final String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(final String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
