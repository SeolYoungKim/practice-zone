package com.study.tobyspringpractice.java_practice.reflection;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReflectionTest2 {
    @Test
    void invokeMethod() throws Exception {
        final String name = "Spring";

        assertThat(name.length()).isEqualTo(6);

        final Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer) lengthMethod.invoke(name)).isEqualTo(6);

        assertThat(name.charAt(0)).isEqualTo('S');

        final Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat((Character) charAtMethod.invoke(name, 0)).isEqualTo('S');
    }

    @Test
    void methodFullSignature() throws NoSuchMethodException {
        final Method method = List.class.getMethod("of", Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class, Object.class);

        System.out.println(method);
    }
}
