package com.seongmo.myshop.auth;

import com.seongmo.myshop.auth.dto.LoginRequest;
import com.seongmo.myshop.auth.dto.LoginResponse;
import com.seongmo.myshop.exception.BusinessException;
import com.seongmo.myshop.exception.ErrorCode;
import com.seongmo.myshop.member.Member;
import com.seongmo.myshop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(member.getEmail());
        return new LoginResponse(token);
    }
}
