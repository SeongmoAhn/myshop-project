package com.seongmo.myshop.member;

import com.seongmo.myshop.exception.BusinessException;
import com.seongmo.myshop.member.dto.MemberJoinRequest;
import com.seongmo.myshop.member.dto.MemberJoinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void joinSuccess() {
        // given
        MemberJoinRequest request = new MemberJoinRequest("test@test.com", "1234", "성모");
        given(memberRepository.existsByEmail(request.getEmail())).willReturn(false);
        given(passwordEncoder.encode(request.getPassword())).willReturn("encodedPassword");
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            return new Member(member.getEmail(), member.getPassword(), member.getNickname());
        });

        // when
        MemberJoinResponse response = memberService.join(request);

        // then
        assertThat(response.getEmail()).isEqualTo("test@test.com");
        assertThat(response.getNickname()).isEqualTo("성모");
    }

    @Test
    @DisplayName("중복 이메일로 회원가입 시 예외 발생")
    void joinDuplicateEmail() {
        // given
        MemberJoinRequest request = new MemberJoinRequest("test@test.com", "1234", "성모");
        given(memberRepository.existsByEmail(request.getEmail())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.join(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이미 사용 중인 이메일입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회 시 예외 발생")
    void getMemberNotFound() {
        // given
        given(memberRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.getMember(999L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
