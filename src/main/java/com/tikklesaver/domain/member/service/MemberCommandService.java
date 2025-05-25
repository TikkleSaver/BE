package com.tikklesaver.domain.member.service;
import com.tikklesaver.domain.member.dto.LoginRequestDto;
import com.tikklesaver.domain.member.dto.SignUpRequestDto;
import com.tikklesaver.domain.member.entity.Member;

import com.tikklesaver.global.jwt.dto.JwtDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface MemberCommandService {
    Member signUp(SignUpRequestDto memberSignUpDto) throws Exception;

    JwtDto login(@Valid LoginRequestDto request);

    JwtDto refreshAccessToken(String jwtToken, String refreshToken);

    void logout(Member member);

    void checkId(String loginId);

    void updatePassWord(Member member, String password, String newPassword);

    Member updateProfile(Member member, @NotNull(message = "닉네임은 필수입니다.") String nickname, MultipartFile profilePicture);
}
