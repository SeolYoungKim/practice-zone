package com.study.tobyspringpractice.dependency.constructor;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final String name = "thisIsMemberRepositoryBean";
    private final Map<Long, Member> repository = new HashMap<>();
    private long id;

    @Override
    public Member save(Member member) {
        id++;
        return repository.put(id, member);
    }
}
