package com.study.tobyspringpractice.dependency_lookup;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class User {
    private final SmartPhone smartPhone;

//    public User() {
//        SmartPhoneFactory factory = new SmartPhoneFactory();
//        smartPhone = factory.smartPhone();
//    }


    public User() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SmartPhoneFactory.class);

        smartPhone = context.getBean("smartPhone", SmartPhone.class);
    }

    public User(final SmartPhone smartPhone) {
        this.smartPhone = smartPhone;
    }

    @Override
    public String toString() {
        return smartPhone.getClass().getSimpleName();
    }
}