package com.study.tobyspringpractice.dependency_lookup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class SmartPhoneFactory {  // CGLIB SmartPhoneFactory -> 아래 빈 생성 메서드들 수행
    @Bean
    public SmartPhone callCountSmartPhone() {
        log.info("빈 등록");

        SmartPhone realSmartPhone = smartPhone();
        CallCountSmartPhone callCountSmartPhone = new CallCountSmartPhone(realSmartPhone);

        log.info("realSmartPhone={}", realSmartPhone.getClass().getSimpleName());
        log.info("callCountSmartPhone={}", callCountSmartPhone.getClass().getSimpleName());
        return callCountSmartPhone;
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
