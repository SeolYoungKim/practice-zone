package com.study.tobyspringpractice.spring_practice.factorybean;

public class Message {
    public static Message newMessage(String text) {
        return new Message(text);
    }

    private final String text;

    private Message(final String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
