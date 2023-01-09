package com.study.java_practice.cglib;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);  // 상속 받은 프록시를 만든다
        enhancer.setCallback(new TimeMethodInterceptor(target));

        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }

    @Test
    void cglibForInterface() {
        ServiceInterface target = new ServiceImpl();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ServiceInterface.class);  // 상속 받은 프록시를 만든다
        enhancer.setCallback(new TimeMethodInterceptor(target));

        ServiceInterface proxy = (ServiceInterface) enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();
    }


}
