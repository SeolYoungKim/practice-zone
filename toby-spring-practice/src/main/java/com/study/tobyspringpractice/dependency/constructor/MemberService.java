package com.study.tobyspringpractice.dependency.constructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(final MemberRepository memberRepository) {
        log.info("생성자 주입 완료. 주입된 오브젝트의 타입 = {}", memberRepository.getClass().getSimpleName());
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setMemberRepository(final MemberRepository memberRepository) {
        log.info("수정자 주입 완료. 주입된 오브젝트의 타입 = {}", memberRepository.getClass().getSimpleName());
        this.memberRepository = memberRepository;
    }
}
