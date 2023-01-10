package com.study.tobyspringpractice.dependency_lookup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallCountSmartPhone implements SmartPhone {
    private final SmartPhone realSmartPhone;
    private int count;

    public CallCountSmartPhone(final SmartPhone realSmartPhone) {
        this.realSmartPhone = realSmartPhone;
    }

    @Override
    public void call() {
        count++;
        realSmartPhone.call();
    }
}
