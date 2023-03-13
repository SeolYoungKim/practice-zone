package com.study.tobyspringpractice.config.v6_aop.aspect;

import com.study.tobyspringpractice.trace.TraceStatus;
import com.study.tobyspringpractice.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(final LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // Advisor
    @Around("execution(* com.study.tobyspringpractice.app..*(..))")  // pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {  // advice
        TraceStatus status = null;
        try {
            final Signature signature = joinPoint.getSignature();
            final String message = signature.toShortString();
            status = logTrace.begin(message);

            final Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
