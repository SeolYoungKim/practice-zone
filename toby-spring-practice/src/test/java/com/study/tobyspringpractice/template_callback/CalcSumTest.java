package com.study.tobyspringpractice.template_callback;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.function.BinaryOperator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcSumTest {
    private static final String FILE_PATH = "/Users/seolyoungkim/Desktop/study/toby-spring-practice/src/test/java/com/study/tobyspringpractice/template_callback/numbers.txt";
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void sumOfNumbers() throws IOException {
//        int sum = calculator.calcSum(FILE_PATH);
        String sum = calculator.calcSum(FILE_PATH);
        assertThat(sum).isEqualTo("1234");
    }

    @Test
    void functionalMethodTestSum() throws IOException {
        BinaryOperator<Integer> sumOperator = Integer::sum;
        int result = calculator.calcBy(FILE_PATH, sumOperator);
        assertThat(result).isSameAs(10);
    }

    @Test
    void functionalMethodTestSubtract() throws IOException {
        BinaryOperator<Integer> subtractOperator = (a, b) -> a - b;
        int result = calculator.calcBy(FILE_PATH, subtractOperator);
        assertThat(result).isSameAs(-8);
    }

    @Test
    void functionalMethodTestMultiply() throws IOException {
        BinaryOperator<Integer> multiplyOperator = (a, b) -> a * b;
        int result = calculator.calcBy(FILE_PATH, multiplyOperator);
        assertThat(result).isSameAs(24);
    }

    @Test
    void functionalMethodTestDivide() throws IOException {
        BinaryOperator<Integer> divideOperator = (a, b) -> a / b;
        int result = calculator.calcBy(FILE_PATH, divideOperator);
        assertThat(result).isSameAs(0);
    }

    @Test
    void functionalMethodTestSum2() throws IOException {
        LineCallback<Integer> callback = (str, num) -> num + Integer.parseInt(str);
        int result = calculator.calcByVer2(FILE_PATH, callback, 0);
        assertThat(result).isSameAs(10);
    }

    @Test
    void functionalMethodTestSubtract2() throws IOException {
        LineCallback<Integer> callback = (str, num) -> num - Integer.parseInt(str);
        int result = calculator.calcByVer2(FILE_PATH, callback, 0);
        assertThat(result).isSameAs(-10);
    }

    @Test
    void functionalMethodTestMultiply2() throws IOException {
        LineCallback<Integer> callback = (str, num) -> num * Integer.parseInt(str);
        int result = calculator.calcByVer2(FILE_PATH, callback, 1);
        assertThat(result).isSameAs(24);
    }

    @Test
    void functionalMethodTestDivide2() throws IOException {
        LineCallback<Integer> callback = (str, num) -> num / Integer.parseInt(str);
        int result = calculator.calcByVer2(FILE_PATH, callback, 1);
        assertThat(result).isSameAs(0);
    }
}
