package com.tikklesaver.domain.member.service;
import com.tikklesaver.domain.member.dto.LoginRequestDto;
import com.tikklesaver.domain.member.dto.SignUpRequestDto;
import com.tikklesaver.domain.member.entity.Member;

import com.tikklesaver.global.jwt.dto.JwtDto;
import jakarta.validation.Valid;

public interface MemberCommandService {
    Member signUp(SignUpRequestDto memberSignUpDto) throws Exception;

    JwtDto login(@Valid LoginRequestDto request);

    JwtDto refreshAccessToken(String jwtToken, String refreshToken);

    void logout(Member member);
}
