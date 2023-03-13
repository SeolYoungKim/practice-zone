package com.study.tobyspringpractice.config.v6_aop;

import com.study.tobyspringpractice.config.v6_aop.aspect.LogTraceAspect;
import com.study.tobyspringpractice.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
