package com.tikklesaver.domain.member.service;
import com.tikklesaver.domain.member.dto.CustomUserInfoDto;
import com.tikklesaver.domain.member.dto.LoginRequestDto;
import com.tikklesaver.domain.member.dto.SignUpRequestDto;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.JwtHandler;
import com.tikklesaver.global.jwt.dto.JwtDto;
import com.tikklesaver.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;



    @Override
    public Member signUp(SignUpRequestDto memberSignUpDto) throws Exception {
        Optional<Member> optionalMember = memberRepository.findByLoginId(memberSignUpDto.getLoginId());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
                throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(memberSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .loginId(memberSignUpDto.getLoginId())
                .password(memberSignUpDto.getPassword())
                .nickname(memberSignUpDto.getNickname())
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);


        return member;
    }

    @Override
    public JwtDto login(LoginRequestDto dto) {
        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        //암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if (!passwordEncoder.matches(password, optionalMember.get().getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        Member member = optionalMember.get();
        CustomUserInfoDto info = new CustomUserInfoDto(member.getId(),member.getLoginId(),member.getPassword(),member.getNickname(),"USER");

        String refreshToken = jwtUtil.createRefreshToken(info);
        String accessToken = jwtUtil.createAccessToken(info);

        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return new JwtDto(accessToken, refreshToken);
    }


    @Override
    public JwtDto refreshAccessToken(String accessToken, String refreshToken) throws JwtHandler {
        accessToken = accessToken.substring(7);
        refreshToken = refreshToken.substring(7);

        //refreshToken 유효성 검증 / accessToken은 이미 만료가 지나도 상관 없는 상태
        if (!jwtUtil.isValidToken(refreshToken)) {
            throw new JwtHandler(ErrorStatus.INVALID_TOKEN);
        }else{
            //accessToken에서 loginId 추출 -> db에 refreshToken 조회
            String loginId = jwtUtil.getUserId(accessToken);
            Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
            Member member = optionalMember.get();

            String dbToken = member.getRefreshToken();

            if(dbToken.equals(refreshToken)){
                CustomUserInfoDto info = new CustomUserInfoDto(member.getId(),member.getLoginId(),member.getPassword(),member.getNickname(),"USER");

                refreshToken = jwtUtil.createRefreshToken(info);
                accessToken = jwtUtil.createAccessToken(info);

                member.setRefreshToken(refreshToken);
                memberRepository.save(member);
            }else {
                throw new JwtHandler(ErrorStatus.INVALID_REFRESH_TOKEN);
            }
        }
        return new JwtDto(accessToken, refreshToken);
    }

}


