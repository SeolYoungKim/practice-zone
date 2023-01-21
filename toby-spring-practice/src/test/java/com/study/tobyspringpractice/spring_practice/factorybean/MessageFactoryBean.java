package com.study.tobyspringpractice.spring_practice.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageFactoryBean implements FactoryBean<Message> {
    private String text;

    @Autowired
    public void setText(@Value("Factory Bean") String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {  // 팩토리 빈의 동작방식에 관한 설정. 만들어진 빈 오브젝트는 싱글톤으로 스프링이 관리해줌.
        return false;
    }
}
