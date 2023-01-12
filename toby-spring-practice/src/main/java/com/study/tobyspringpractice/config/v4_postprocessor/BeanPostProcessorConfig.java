package com.study.tobyspringpractice.config.v4_postprocessor;

import com.study.tobyspringpractice.app.LogTraceAdvice;
import com.study.tobyspringpractice.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import com.study.tobyspringpractice.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
public class BeanPostProcessorConfig {
//    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("com.study.tobyspringpractice.app",
                getAdvisor(logTrace));
    }

    private Advisor getAdvisor(final LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
