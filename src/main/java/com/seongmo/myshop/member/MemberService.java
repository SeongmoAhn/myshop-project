package com.seongmo.myshop.member;

import com.seongmo.myshop.member.dto.MemberJoinRequest;
import com.seongmo.myshop.member.dto.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final Map<Long, MemberJoinResponse> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public MemberJoinResponse join(MemberJoinRequest request) {
        Long id = sequence.getAndIncrement();
        MemberJoinResponse member = new MemberJoinResponse(id, request.getEmail(), request.getNickname());
        store.put(id, member);
        return member;
    }

    public MemberJoinResponse getMember(Long id) {
        return store.get(id);
    }
}
