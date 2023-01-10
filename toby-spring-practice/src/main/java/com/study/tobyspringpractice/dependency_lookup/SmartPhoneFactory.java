package com.study.tobyspringpractice.dependency_lookup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SmartPhoneFactory {
    @Bean
    public SmartPhone callCountSmartPhone() {
        return new CallCountSmartPhone(smartPhone());
    }

    @Bean
    public SmartPhone smartPhone() {
        return new Iphone1024();
    }

    @Bean
    public User user() {
        return new User(smartPhone());
    }
}
