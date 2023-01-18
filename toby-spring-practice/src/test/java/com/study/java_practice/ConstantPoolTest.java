package com.study.java_practice;

import java.lang.reflect.Field;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ConstantPoolTest {
    @Test
    void name() {
        Field[] declaredFields = TestA.class.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields));
    }

    static class TestA {
        private static final String CONST1 = "1";
        private static final String CONST2 = "2";
        private static final String CONST3 = "3";
        private static final String CONST4 = "4";
    }
}
