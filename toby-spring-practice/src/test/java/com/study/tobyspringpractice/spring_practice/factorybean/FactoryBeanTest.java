package com.study.tobyspringpractice.spring_practice.factorybean;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.tobyspringpractice.spring_practice.proxy.Hello;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    void getMessageFromFactoryBean() {
        final Object message = context.getBean("messageFactoryBean");

        assertThat(message.getClass()).isEqualTo(Message.class);
        assertThat(((Message) message).text()).isEqualTo("Factory Bean");
    }

    @Test
    void getFactoryBean() {
        final Object factoryBean = context.getBean("&messageFactoryBean");
        assertThat(factoryBean.getClass()).isEqualTo(MessageFactoryBean.class);
    }

    @Test
    void getProxyFromFactoryBean() {
        final Object hello = context.getBean("txProxyFactoryBean");

        assertThat(hello.getClass().getName()).startsWith("jdk.proxy");
        assertThat(((Hello) hello).sayHello("kim")).isEqualTo("Hello kim");
    }

    @Test
    void getProxyFactoryBean() {
        final TxProxyFactoryBean txFactory = (TxProxyFactoryBean) context.getBean("&txProxyFactoryBean");

        assertThat(txFactory.getObjectType()).isEqualTo(Hello.class);
    }
}
