package com.study.tobyspringpractice.spring_practice.dependency_injection;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DependencyBetweenClassTest {
    static class A {
        private final B b;

        public A(final B b) {
            this.b = b;
        }

        public int calculateByB(int num1, int num2) {
            return b.calculate(num1, num2);
        }
    }

    static class B {
        public int calculate(int num1, int num2) {
            return num1 + num2;
        }
    }

    @DisplayName("A의 calculateByB 메서드는 int 값 두 개를 더한 값을 반환 해준다.")
    @Test
    void test1() {
        A a = new A(new B());
        assertThat(a.calculateByB(1, 2)).isEqualTo(3);
    }
}
