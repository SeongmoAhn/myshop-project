package com.seongmo.myshop.member;

import com.seongmo.myshop.exception.BusinessException;
import com.seongmo.myshop.exception.ErrorCode;
import com.seongmo.myshop.member.dto.MemberJoinRequest;
import com.seongmo.myshop.member.dto.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.seongmo.myshop.member.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberJoinResponse join(MemberJoinRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = new Member(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        );

        Member savedMember = memberRepository.save(member);

        return new MemberJoinResponse(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getNickname()
        );
    }

    public MemberJoinResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        return new MemberJoinResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname()
        );
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAllWithItems()
                .stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }
}
