package com.study.java_practice;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class InitializationTest {
    @DisplayName("SubClass가 초기화 될 경우, SuperClass가 먼저 초기화된다.")
    @Test
    void initTest() {
        Sub sub = new Sub();
    }

    @DisplayName("인스턴스를 생성하지 않을 경우, 초기화 되지 않는다.")
    @Test
    void initTest2() {
        One one = null;
    }

    @DisplayName("정적 필드를 선언하는 클래스만 초기화 된다.")
    @Test
    void initTest3() {
        log.info("택시 번호는 : {}", Sub.taxi);
    }

    @DisplayName("인터페이스는 SuperInterface를 초기화하지 않는다.")
    @Test
    void initTest4() {
        TestForInterface.main(new String[]{""});
    }

    /**
     * 아래는 클래스 초기화를 위한 테스트 코드
     */
    static class Super {
        static int taxi = 4885;

        static {
            log.info("Super가 초기화 되었습니다.");
        }
    }

    static class Sub extends Super {
        static {
            log.info("Sub가 초기화 되었습니다.");
        }
    }

    static class One {
        static {
            log.info("One이 초기화 되었습니다.");
        }
    }

    /**
     * 아래는 인터페이스 초기화 테스트를 위한 코드
     */
    interface I {
        int i = 1, ii = TestForInterface.out("ii", 2);
    }

    interface J extends I {
        int j = TestForInterface.out("j", 3), jj = TestForInterface.out("jj", 4);
    }

    interface K extends J {
        int k = TestForInterface.out("k", 5);
    }

    static class TestForInterface {
        public static void main(String[] args) {
            log.info(J.i + "");
            log.info(K.j + "");
        }

        static int out(String s, int i) {
            log.info(s + "=" + i);
            return i;
        }
    }
}
