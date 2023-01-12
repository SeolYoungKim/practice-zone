package com.study.tobyspringpractice;

import com.study.tobyspringpractice.config.v4_postprocessor.BeanPostProcessorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(BeanPostProcessorConfig.class)
@SpringBootApplication
public class TobySpringPracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TobySpringPracticeApplication.class, args);
    }
}
