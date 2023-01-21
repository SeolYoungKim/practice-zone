package com.study.tobyspringpractice.spring_practice.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class TransactionHandler implements InvocationHandler {
    private final Object target;
    private final PlatformTransactionManager transactionManager;
    private final String[] patterns;

    public TransactionHandler(final Object target,
            final PlatformTransactionManager transactionManager,
            final String[] patterns) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args)
            throws Throwable {
        final String methodName = method.getName();
        if (PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return invokeInTransaction(method, args);
        }

        return method.invoke(target, args);
    }

    private Object invokeInTransaction(final Method method, final Object[] args) {
        final TransactionStatus status = transactionManager.getTransaction(
                new DefaultTransactionDefinition());

        try {
            log.info("트랜잭션 커밋 시도");

            final Object result = method.invoke(target, args);
            transactionManager.commit(status);

            log.info("트랜잭션 커밋 완료");
            return result;
        } catch (InvocationTargetException e) {
            log.info("트랜잭션 커밋 실패");
            transactionManager.rollback(status);
            throw new RuntimeException(e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
