package com.study.java_practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ConstructTest {
    @DisplayName("인스턴스 생성 중 동적 디스패치")
    @Test
    void name() {
        // 자식 클래스 생성자 호출 -> 인스턴스 변수가 0(기본값)으로 초기화(아직 인스턴스가 생성되지 않음) -> 부모 클래스 생성자 호출
        // 부모 클래스 생성자 내부의 printThree()를 실행 -> 하지만 포인터는 "자식 클래스"를 가리키고 있으므로, 자식 클래스의 printThree()가 실행된다.
        // 0 출력 -> 부모 클래스 생성자 끝 -> 자식 클래스의 인스턴스 변수 초기화 -> 자식 클래스 생성자 끝
        SubTest subTest = new SubTest();

        // 자식 클래스의 인스턴스 변수가 초기화 되어있으므로, 3 출력
        subTest.printThree();
    }

    static class Super {
        public Super() {
            printThree();  // 자식 클래스의 printThree()가 호출됨
        }

        void printThree() {
            log.info("three");
        }
    }

    static class SubTest extends Super {
        int three = 3;

        public SubTest() {
            super();
        }

        void printThree() {
            log.info(three + "");
        }
    }
}
