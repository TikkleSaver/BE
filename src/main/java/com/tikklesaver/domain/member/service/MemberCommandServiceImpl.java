package com.tikklesaver.domain.member.service;
import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.Challenge.repository.ChallengeScrapRepository;
import com.tikklesaver.domain.Challenge.repository.JoinChallengeRepository;
import com.tikklesaver.domain.member.dto.CustomUserInfoDto;
import com.tikklesaver.domain.member.dto.LoginRequestDto;
import com.tikklesaver.domain.member.dto.SignUpRequestDto;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.entity.MemberCategory;
import com.tikklesaver.domain.member.repository.MemberCategoryRepository;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.JwtHandler;
import com.tikklesaver.global.apiPayload.exception.handler.MemberHandler;
import com.tikklesaver.global.apiPayload.exception.handler.TempHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.common.Uuid;
import com.tikklesaver.global.jwt.dto.JwtDto;
import com.tikklesaver.global.jwt.util.JwtUtil;
import com.tikklesaver.global.repository.UuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final WishRepository wishRepository;
    private final JoinChallengeRepository joinChallengeRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;
    private final ChallengeScrapRepository challengeScrapRepository;



    @Override
    public Member signUp(SignUpRequestDto memberSignUpDto) throws Exception {
        Optional<Member> optionalMember = memberRepository.findByLoginId(memberSignUpDto.getLoginId());

        if (optionalMember.isPresent()) {
                throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXISTS);
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
    public JwtDto refreshAccessToken(Member member, String refreshToken) throws JwtHandler {
        refreshToken = refreshToken.substring(7);
        String accessToken;
        //refreshToken 유효성 검증
        if (!jwtUtil.isValidToken(refreshToken)) {
            throw new JwtHandler(ErrorStatus.INVALID_TOKEN);
        }else{
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

    @Override
    public void logout(Member member) {
        member.setRefreshToken(null); // 토큰 제거
        memberRepository.save(member); // 변경사항 저장
    }

    @Override
    public void checkId(String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXISTS);
        }
    }

    @Override
    public void updatePassWord(Member member, String password, String newPassword) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MemberHandler(ErrorStatus.PASSWORD_UNAUTHORIZED);
        }

        member.setPassword(newPassword);
        member.passwordEncode(passwordEncoder);

        // 변경된 회원 정보 저장
        memberRepository.save(member);
    }


    @Override
    public Member updateProfile(Member member, String nickname, MultipartFile profilePicture)  {
        // 닉네임이 제공된 경우 업데이트
        log.info(nickname);
        if (nickname != null) {
            member.setNickname(nickname);
        }

        // 프로필 URL이 제공된 경우 업데이트
        if (profilePicture != null) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(uuid).build());

            String pictureUrl = s3Manager.uploadFile(s3Manager.generateMembersKeyName(savedUuid),profilePicture);

            //원래 이미지 삭제

            if (member.getProfileUrl() != null) {
                s3Manager.deleteFile(member.getProfileUrl());
            }

            member.setProfileUrl(pictureUrl);
        }
        // 변경된 회원 정보 저장
        memberRepository.save(member);

        return member;
    }

    @Override
    public void saveCategories(Long memberId, List<Long> categoryList) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        for (Long categoryId : categoryList) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new TempHandler(ErrorStatus.CATEGORY_NOT_FOUND));

            MemberCategory memberCategory = MemberCategory.builder()
                    .member(member)
                    .category(category)
                    .build();
            memberCategoryRepository.save(memberCategory);
        }
    }

    @Override
    public void saveGoalCost(Long memberId, Long goalCost) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        member.setGoalCost(goalCost);

        // 변경된 회원 정보 저장
        memberRepository.save(member);
    }

    @Override
    public int getWishListCount(Long memberId) {
        return wishRepository.countByMemberId(memberId);
    }

    @Override
    public int getChallengeCount(Long memberId) {
        return joinChallengeRepository.countByMemberId(memberId);
    }

    @Override
    public int getFriendCount(Long memberId) {
        return 0;
    }

    public List<Challenge> getScrappedChallenges(Long memberId) {
        List<ChallengeScraped> scrapedList = challengeScrapRepository.findAllByMemberId(memberId);

        return Optional.ofNullable(scrapedList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(ChallengeScraped::getChallenge)
                .collect(Collectors.toList());
    }


    // 지출 목표 금액 수정(지출 달력 페이지)
    @Override
    public Member saveExpenseGoalCost(Long memberId, Long goalCost) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        member.setGoalCost(goalCost);

        return memberRepository.save(member);
    }

    // 지출 목표 금액 조회(지출 달력 페이지)
    @Override
    public Long getExpenseGoalCost(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return member.getGoalCost();
    }

    @Override
    public List<Member> searchByNicknameKeyword(String keyword) {
        return memberRepository.searchByNicknameKeyword(keyword);
    }
}


