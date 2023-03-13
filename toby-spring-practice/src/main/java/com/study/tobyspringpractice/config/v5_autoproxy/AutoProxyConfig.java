package com.study.tobyspringpractice.config.v5_autoproxy;

import com.study.tobyspringpractice.app.LogTraceAdvice;
import com.study.tobyspringpractice.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AutoProxyConfig {
//    @Bean
    public Advisor advisor1(final LogTrace logTrace) {
        //pointcut
        //포인트컷으로 1회 차 때 프록시 생성 여부를 판단함
        //이후에 프록시 객체의 메서드를 실행할 때, 포인트컷을 확인하여 프록시 로직을 적용(어드바이스 호출)할지 말지 판단함
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(final LogTrace logTrace) {
        //pointcut
        //포인트컷으로 1회 차 때 프록시 생성 여부를 판단함
        //이후에 프록시 객체의 메서드를 실행할 때, 포인트컷을 확인하여 프록시 로직을 적용(어드바이스 호출)할지 말지 판단함
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.study.tobyspringpractice.app..*(..))");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor3(final LogTrace logTrace) {
        //pointcut
        //포인트컷으로 1회 차 때 프록시 생성 여부를 판단함
        //이후에 프록시 객체의 메서드를 실행할 때, 포인트컷을 확인하여 프록시 로직을 적용(어드바이스 호출)할지 말지 판단함
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.study.tobyspringpractice.app..*(..)) && "
                + "!execution(* com.study.tobyspringpractice.app..noLog(..))");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
