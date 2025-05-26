package com.tikklesaver.domain.member.service;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.member.dto.LoginRequestDto;
import com.tikklesaver.domain.member.dto.SignUpRequestDto;
import com.tikklesaver.domain.member.entity.Member;

import com.tikklesaver.global.jwt.dto.JwtDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberCommandService {
    Member signUp(SignUpRequestDto memberSignUpDto) throws Exception;

    JwtDto login(@Valid LoginRequestDto request);

    JwtDto refreshAccessToken(String jwtToken, String refreshToken);

    void logout(Member member);

    void checkId(String loginId);

    void updatePassWord(Member member, String password, String newPassword);

    Member updateProfile(Member member, @NotNull(message = "닉네임은 필수입니다.") String nickname, MultipartFile profilePicture);

    void saveCategories(@NotNull(message = "아이디는 필수") Long memberId, @NotNull List<Long> categoryList);

    void saveGoalCost(@NotNull(message = "아이디는 필수") Long memberId, @NotNull Long goalCost);

    int getWishListCount(Member member);

    int getChallengeCount(Member member);

    int getFriendCount(Member member);

    List<Challenge> getScrappedChallenges(Member member);

    // 지출 목표 금액 수정(지출 달력 페이지)
    Member saveExpenseGoalCost(Long memberId, Long goalCost);

    // 지출 목표 금액 조회(지출 달력 페이지)
    Long getExpenseGoalCost(Long memberId);
}
