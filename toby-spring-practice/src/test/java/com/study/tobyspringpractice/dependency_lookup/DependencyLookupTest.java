package com.study.tobyspringpractice.dependency_lookup;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@SpringBootTest
public class DependencyLookupTest {
    @Test
    void name() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(
                SmartPhoneFactory.class);

        annotationConfigApplicationContext.register();

    }

}
