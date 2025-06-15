package com.tikklesaver.domain.member.controller;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.member.converter.MemberConverter;
import com.tikklesaver.domain.member.dto.*;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.service.MemberCommandService;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import com.tikklesaver.global.jwt.dto.JwtDto;
import com.tikklesaver.global.jwt.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberCommandService memberCommandService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDto.MemberInfoDTO> signUp(@RequestBody @Valid SignUpRequestDto memberSignUpDTO) throws Exception {
        Member member = memberCommandService.signUp(memberSignUpDTO);
        return ApiResponse.onSuccess(MemberConverter.toMemberInfoDTO(member));
    }

    @PostMapping("/login")
    public ApiResponse<JwtDto> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        JwtDto token = memberCommandService.login(request);
        return ApiResponse.onSuccess(token);
    }

    //JWT 테스트를 위한 API
    @GetMapping("/jwt-test")
    public ApiResponse<String> jwtTest(@CurrentMember Member member) {
        log.info(member.getLoginId());
        return ApiResponse.onSuccess("jwtTest 요청 성공");
    }

    @PostMapping("/refresh-token")
    public ApiResponse<JwtDto> refresh(@RequestHeader("Authorization") String refreshToken, @CurrentMember Member member) throws Exception {
        String jwtToken = refreshToken.substring(7); // "Bearer " 제거
        log.info("refreshToken: {}", jwtToken);

        return ApiResponse.onSuccess(memberCommandService.refreshAccessToken(member, refreshToken));
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@CurrentMember Member member) {
        memberCommandService.logout(member);
        return ApiResponse.onSuccess("로그아웃에 성공하였습니다.");
    }

    //아이디 중복 확인
    @PostMapping("/check-id/{id}")
    public ApiResponse<String> checkId(@PathVariable(name = "id") String id) throws Exception {
        memberCommandService.checkId(id);
        return ApiResponse.onSuccess("사용가능한 이메일입니다.");
    }

    @PatchMapping("/users/password")
    public ApiResponse<String> updatePassWord(@CurrentMember Member member, @RequestBody PassWordDto dto) throws Exception {
        memberCommandService.updatePassWord(member, dto.getPassword(), dto.getNewPassword());
        return ApiResponse.onSuccess("비밀번호 변경에 성공했습니다.");
    }

    //프로필 수정
    @PatchMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberResponseDto.MemberInfoDTO> updateProfile
    (@Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
     @RequestPart(value = "profileImg", required = false) MultipartFile profileImg,@CurrentMember Member member,
     @Valid @RequestPart(name="requestDTO") MemberRequestDto.UpdateProfileDTO requestDTO) {
        return ApiResponse.onSuccess(MemberConverter.toMemberInfoDTO(memberCommandService.updateProfile(member, requestDTO.getNickname(), profileImg)));
    }




    //온보딩
    @PostMapping("/users/onboarding/category")
    public ApiResponse<String> saveCategories(@Valid @RequestBody MemberRequestDto.CategoryDTO requestDTO)  {
        memberCommandService.saveCategories(requestDTO.getMemberId(), requestDTO.getCategoryList());
        return ApiResponse.onSuccess("카테고리 저장 완료");
    }

    @PatchMapping("/users/onboarding/goalCost")
    public ApiResponse<String> saveGoalCost(@Valid @RequestBody MemberRequestDto.GoalCostDTO requestDTO) {
        memberCommandService.saveGoalCost(requestDTO.getMemberId(), requestDTO.getGoalCost());
        return ApiResponse.onSuccess("목표 지출액 저장 완료");
    }

    //내 정보 조회
    @GetMapping("/users")
    public ApiResponse<MemberResponseDto.MemberProfileDTO> getProfile(@CurrentMember Member member) throws Exception {
        int wishListNum = memberCommandService.getWishListCount(member.getId());
        int challengeNum = memberCommandService.getChallengeCount(member.getId());
        int friendNum = memberCommandService.getFriendCount(member.getId());
        List<Challenge> challengeScrapedList = memberCommandService.getScrappedChallenges(member.getId());

        return ApiResponse.onSuccess(
                MemberConverter.toMemberProfileDTO(member, wishListNum,
                challengeNum, friendNum, challengeScrapedList));
    }


    // 지출 목표 금액 수정(지출 달력 페이지)
    @PatchMapping("/users/goalCost")
    @Operation(summary = "특정 사용자의 지출 목표 금액 수정 API")
    @Parameters({
            @Parameter(name = "goalCost", description = "지출 목표 금액", required = true)
    })
    public ApiResponse<MemberResponseDto.MemberGoalCostDTO> saveExpenseGoalCost(
            @CurrentMember Member member,
            @RequestParam Long goalCost) {

        Member goalCostMember =  memberCommandService.saveExpenseGoalCost(member.getId(), goalCost);
        return ApiResponse.onSuccess(MemberConverter.toUpdateMemberGoalCostDTO(goalCostMember));
    }

    // 지출 목표 금액 조회(지출 달력 페이지)
    @GetMapping("/users/goalCost")
    @Operation(summary = "특정 사용자의 지출 목표 금액 조회 API")
    @Parameters({
            @Parameter(name = "memberId", description = "지출 목표 금액의 주인인 사용자의 ID", required = false)
    })
    public ApiResponse<MemberResponseDto.GetMemberGoalCostDTO> getExpenseGoalCost(
            @CurrentMember Member viewer,
            @RequestParam(value = "memberId", required = false) Long memberId) {

        if (memberId == null) {
            Long goalCost =  memberCommandService.getExpenseGoalCost(viewer.getId());
            return ApiResponse.onSuccess(MemberConverter.toGetMemberGoalCostDTO(viewer.getId(), viewer.getId(), goalCost));
        } else{
            Long goalCost =  memberCommandService.getExpenseGoalCost(memberId);
            return ApiResponse.onSuccess(MemberConverter.toGetMemberGoalCostDTO(viewer.getId(), memberId, goalCost));
        }
    }

    @GetMapping("/users/search")
    public ApiResponse<List<MemberResponseDto.MemberInfoDTO>> searchMembers(
            @RequestParam int pageNum, String keyword) {
        List<Member> members = memberCommandService.searchByNicknameKeyword(keyword, pageNum);
        return ApiResponse.onSuccess(MemberConverter.toMemberInfoListDTO(members));
    }
}
