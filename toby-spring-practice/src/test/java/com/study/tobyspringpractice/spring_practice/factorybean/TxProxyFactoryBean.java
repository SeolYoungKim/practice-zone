package com.study.tobyspringpractice.spring_practice.factorybean;

import com.study.tobyspringpractice.spring_practice.proxy.HelloTarget;
import com.study.tobyspringpractice.spring_practice.proxy.TransactionHandler;
import java.lang.reflect.Proxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class TxProxyFactoryBean implements FactoryBean<Object> {
    private final PlatformTransactionManager transactionManager;
    private final Object target = new HelloTarget();  // 편의를 위해 타겟 클래스를 고정함
    private final String[] patterns = {"say*"};  // 편의를 위해 고정
    private final Class<?> serviceInterface;
//            = Hello.class;  // 편의를 위해 고정

    public TxProxyFactoryBean(final PlatformTransactionManager transactionManager,
            @Value("com.study.tobyspringpractice.spring_practice.proxy.Hello") final Class<?> serviceInterface) {
        this.transactionManager = transactionManager;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {  // TxProxyFactoryBean은 getObject()가 반환하는 클래스를 빈으로 등록한다.
        final TransactionHandler txHandler = new TransactionHandler(target, transactionManager, patterns);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{serviceInterface},
                txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
