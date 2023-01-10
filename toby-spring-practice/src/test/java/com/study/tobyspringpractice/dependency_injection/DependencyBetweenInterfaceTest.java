package com.study.tobyspringpractice.dependency_injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DependencyBetweenInterfaceTest {
    @Test
    void test1() {
        MacBook macBook = new MacBook();
        Client client = new Client(macBook);
        client.doCodingTest();
    }

    @Test
    void test2() {
        GalaxyBook galaxyBook = new GalaxyBook();
        Client client = new Client(galaxyBook);
        client.doCodingTest();
    }

    @Test
    void test3() {
        DiContainer diContainer = new DiContainer();
        Client clientHasMacBook = diContainer.client();
        clientHasMacBook.doCodingTest();

        Client clientHasGalaxyBook = diContainer.clientHasGalaxyBook();
        clientHasGalaxyBook.doCodingTest();
    }

    static class DiContainer {
        public Client client() {
            MacBook macBook = new MacBook();
            return new Client(macBook);
        }

        public Client clientHasGalaxyBook() {
            GalaxyBook galaxyBook = new GalaxyBook();
            return new Client(galaxyBook);
        }
    }

    static class Client {
        private final Laptop laptop;

        public Client(final Laptop laptop) {
            this.laptop = laptop;
        }

        public void doCodingTest() {
            laptop.powerOn();
            log.info("{}이 켜졌습니다.", laptop.getClass().getSimpleName());
            log.info("백준 코딩테스트 풀이를 시작합니다.");
            log.info("틀렸습니다. 시간 초과! 메모리 초과!");
            log.info("화가 나서 컴퓨터를 끕니다.");
            laptop.powerOff();
        }
    }

    interface Laptop {
        void powerOn();

        void powerOff();
    }

    static class MacBook implements Laptop {

        @Override
        public void powerOn() {
            log.info("맥북이 켜졌습니다.");
        }

        @Override
        public void powerOff() {
            log.info("맥북이 꺼졌습니다.");
        }
    }

    static class GalaxyBook implements Laptop {

        @Override
        public void powerOn() {
            log.info("갤럭시북이 켜졌습니다.");
        }

        @Override
        public void powerOff() {
            log.info("갤럭시북이 꺼졌습니다.");
        }
    }

}
