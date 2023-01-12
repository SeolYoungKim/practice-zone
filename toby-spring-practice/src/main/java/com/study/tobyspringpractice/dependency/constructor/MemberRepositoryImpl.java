package com.study.tobyspringpractice.dependency.constructor;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Getter
@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final String name = "thisIsMemberRepositoryBean";
    private final Map<Long, Member> repository = new HashMap<>();
    private long id;

    @Override
    public Member save(Member member) {
        id++;
        log.info("메서드가 실행되었습니다.");
        return repository.put(id, member);
    }
}
