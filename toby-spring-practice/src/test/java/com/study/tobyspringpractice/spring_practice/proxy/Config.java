package com.study.tobyspringpractice.spring_practice.proxy;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private final TransactionAdvice transactionAdvice;
    private final NameMatchClassMethodPointcut pointcut;

    public Config(final TransactionAdvice transactionAdvice,
            final NameMatchClassMethodPointcut pointcut) {
        this.transactionAdvice = transactionAdvice;
        this.pointcut = pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice);
    }
}
