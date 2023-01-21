package com.study.tobyspringpractice.spring_practice.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Slf4j
@Component
public class TransactionAdvice implements MethodInterceptor {
    private final PlatformTransactionManager transactionManager;

    public TransactionAdvice(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {  // 프록시로부터 MethodInvocation을 전달받음.
        final TransactionStatus status = transactionManager.getTransaction(
                new DefaultTransactionDefinition());
        try {
            log.info("커밋을 시도합니다.");

            final Object result = invocation.proceed();
            transactionManager.commit(status);

            log.info("커밋이 완료되었습니다.");
            return result;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
