package com.study.tobyspringpractice.spring_practice.dependency.constructor;

import static org.junit.jupiter.api.Assertions.*;

import com.study.tobyspringpractice.dependency.constructor.MemberRepository;
import com.study.tobyspringpractice.dependency.constructor.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    void test() {
        memberService.setMemberRepository(memberRepository);
    }
}