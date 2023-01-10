package com.study.tobyspringpractice.dependency_lookup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Iphone1024 implements SmartPhone {
    @Override
    public void call() {
        log.info("아이폰1024로 전화를 겁니다.");
    }
}
