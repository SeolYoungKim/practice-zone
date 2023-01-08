package com.study.tobyspringpractice.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MemberRepository {
    private final String name = "thisIsMemberRepositoryBean";
}
