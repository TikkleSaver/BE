package com.tikklesaver.domain.member.controller;

import com.tikklesaver.domain.member.converter.MemberConverter;
import com.tikklesaver.domain.member.dto.LoginRequestDto;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
import com.tikklesaver.domain.member.dto.SignUpRequestDto;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.service.MemberCommandService;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import com.tikklesaver.global.jwt.dto.JwtDto;
import com.tikklesaver.global.jwt.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberCommandService memberCommandService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 API입니다. ")
    public ApiResponse<MemberResponseDto.MemberInfoDTO> signUp(@RequestBody @Valid SignUpRequestDto memberSignUpDTO) throws Exception {
        Member member = memberCommandService.signUp(memberSignUpDTO);
        return ApiResponse.onSuccess(MemberConverter.toMemberInfoDTO(member));
    }

    @PostMapping("/login")
    public ApiResponse<JwtDto> getMemberProfile(
            @Valid @RequestBody LoginRequestDto request
    ) {
        JwtDto token = memberCommandService.login(request);
        return ApiResponse.onSuccess(token);
    }

    //JWT 서비스 테스트를 위한 API
    @GetMapping("/jwt-test")
    @Operation(summary = "jwtTest 요청", description = "서버 테스트용 api입니다. 연동x")
    public ApiResponse<String> jwtTest(@CurrentMember Member member) {
        log.info(member.getLoginId());
        return ApiResponse.onSuccess("jwtTest 요청 성공");
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "액세스 토큰 재발급", description = "accessToken 재발급하는 API입니다.accessToken 필요")
    public ApiResponse<JwtDto> refresh(@RequestHeader("Authorization") String accessToken, @RequestHeader("Authorization-refresh") String refreshToken) throws Exception {
        String jwtToken = accessToken.substring(7); // "Bearer " 제거
        log.info("AccessToken: {}", jwtToken);

        return ApiResponse.onSuccess(memberCommandService.refreshAccessToken(accessToken, refreshToken));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃",description = "로그아웃 하는 API입니다.accessToken 필요")
    public ApiResponse<String> logout(@CurrentMember Member member) {
        memberCommandService.logout(member);
        return ApiResponse.onSuccess("로그아웃에 성공하였습니다.");
    }

}
