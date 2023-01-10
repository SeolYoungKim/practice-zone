package com.study.tobyspringpractice.dependency_lookup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GalaxyZzzzFlip implements SmartPhone {
    @Override
    public void call() {
        log.info("갤럭시 ZZZZ 플립으로 전화를 겁니다.");
    }
}
