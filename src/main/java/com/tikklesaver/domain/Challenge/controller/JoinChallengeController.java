package com.tikklesaver.domain.challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.dto.joinChallenge.JoinChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.service.Challenge.ChallengeQueryService;
import com.tikklesaver.domain.Challenge.service.JoinChallenge.JoinChallengeCommandService;
import com.tikklesaver.domain.Challenge.service.JoinChallenge.JoinChallengeQueryService;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-challenges")
public class JoinChallengeController {

    private final JoinChallengeCommandService joinChallengeCommandService;
    private final ChallengeQueryService challengeQueryService;
    private final JoinChallengeQueryService joinChallengeQueryService;

    @PostMapping("/{challengeId}")
    @Operation(summary = "챌린지 참여 요청 API")
    public ApiResponse<JoinChallengeResponseDTO> joinChallenge(
            @CurrentMember Member member,
            @PathVariable(name = "challengeId") Long challengeId){

        JoinChallenge joinChallenge = joinChallengeCommandService.joinChallenge(member.getId(),challengeId);
        return ApiResponse.onSuccess(ChallengeConverter.toJoinChallengeResultDTO(joinChallenge));
    }

    @GetMapping("/{challengeId}")
    @Operation(summary = "챌린지 신청 페이지 조회 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO> getChallengePreview(  @CurrentMember Member member, @PathVariable(name = "challengeId") Long challengeId){

        ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO challengePreview = challengeQueryService.getChallengePreview(member.getId(),challengeId);

        return ApiResponse.onSuccess(challengePreview);

    }

    @GetMapping("/{challengeId}/challenger-list")
    @Operation(summary = "챌린지 소속 챌린저 조회 API ")
    public ApiResponse<ChallengeResponseDTO.DetailChallengerTabListResponseDTO> getChallengerList(@PathVariable(name = "challengeId") Long challengeId, @RequestParam(name="page")Integer page){
        
        Page<JoinChallenge> challengerList = joinChallengeQueryService.getChallengeMembers(challengeId, page);

        return ApiResponse.onSuccess(ChallengeConverter.detailChallengerTabListDTO(challengerList));

    }

    @GetMapping("/{challengeId}/request-list")
    @Operation(summary = "챌린지 참여 요청 챌린저 조회 API ")
    public ApiResponse<ChallengeResponseDTO.DetailChallengerTabListResponseDTO> getRequestChallengerList(@PathVariable(name = "challengeId") Long challengeId, @RequestParam(name="page")Integer page){

        Page<JoinChallenge> challengerList = joinChallengeQueryService.getChallengeRequestMembers(challengeId, page);

        return ApiResponse.onSuccess(ChallengeConverter.detailChallengerTabListDTO(challengerList));

    }

    @PostMapping("/{joinChallengeId}/accept")
    @Operation(summary = "챌린지 참여 요청 수락 API")
    public ApiResponse<JoinChallengeResponseDTO> acceptChallenge(
            @CurrentMember Member member,
            @PathVariable(name = "joinChallengeId") Long joinChallengeId){

        JoinChallenge joinChallenge = joinChallengeCommandService.acceptChallenge(member.getId(),joinChallengeId);
        return ApiResponse.onSuccess(ChallengeConverter.toJoinChallengeResultDTO(joinChallenge));
    }

    @DeleteMapping("/{joinChallengeId}/delete")
    @Operation(summary = "챌린지 참여 요청 거절 API, 챌린지 나가기 api")
    public ApiResponse<String> rejectChallenge(
            @CurrentMember Member member,
            @PathVariable(name = "joinChallengeId") Long joinChallengeId){

         joinChallengeCommandService.rejectChallenge(member.getId(),joinChallengeId);
        return ApiResponse.onSuccess("joinChallenge가 삭제되었습니다.");
    }

}
