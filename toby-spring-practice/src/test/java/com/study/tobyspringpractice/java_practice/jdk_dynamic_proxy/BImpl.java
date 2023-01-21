package com.study.tobyspringpractice.java_practice.jdk_dynamic_proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BImpl implements BInterface{
    @Override
    public String call() {
        log.info("B 호출");
        return "b";
    }

    @Override
    public void call2() {
        log.info("B의 call2 메서드가 호출되었습니다.");
    }

    @Override
    public void methodNotUseProxy() {
        log.info("Proxy 내부 로직이 적용되지 않은 메서드입니다.");
    }
}
