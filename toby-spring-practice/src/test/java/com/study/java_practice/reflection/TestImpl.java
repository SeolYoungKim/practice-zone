package com.study.java_practice.reflection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestImpl implements TestInterface{
    @Override
    public void call() {
        log.info("구현체의 메서드가 호출되었습니다.");
    }
}
