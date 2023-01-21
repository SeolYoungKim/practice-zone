package com.study.tobyspringpractice.spring_practice.template_callback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Calculator {
    public String calcSum(String filePath) throws IOException {
//        LineCallback<Integer> sumCallback = (str, num) -> num + Integer.parseInt(str);
        LineCallback<String> sumCallback = (str, num) -> num + str;
        return lineReadTemplate(filePath, sumCallback, "");
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback)
            throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return callback.doSomethingWithReader(br);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal)
            throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            T res = initVal;
            String line;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }

            return res;
        }
    }

    public int calcBy(String filePath, BinaryOperator<Integer> binaryOperator) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Integer> numbers = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.valueOf(line));
            }

            return numbers.stream()
                    .reduce(binaryOperator)
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public <T> T calcByVer2(String filePath, LineCallback<T> callback, T initValue) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            T result = initValue;
            String line;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }

            return result;
        }
    }
}
