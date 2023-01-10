package com.study.tobyspringpractice.dependency_lookup;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class UserTest {

    @DisplayName("User는 빈으로 등록된 구현체인 Iphone1024와 동적으로 의존관계를 맺는다.")
    @Test
    void name() {
        User user = new User();
        Assertions.assertThat(user.toString()).isEqualTo("Iphone1024");
    }
}