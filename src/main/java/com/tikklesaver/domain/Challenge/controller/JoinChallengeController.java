package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.dto.joinChallenge.JoinChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.service.Challenge.ChallengeQueryService;
import com.tikklesaver.domain.Challenge.service.JoinChallenge.JoinChallengeCommandService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-challenges")
public class JoinChallengeController {

    private final JoinChallengeCommandService joinChallengeCommandService;
    private final ChallengeQueryService challengeQueryService;

    @PostMapping("/{challengeId}")
    @Operation(summary = "챌린지 참여 요청 API")
    public ApiResponse<JoinChallengeResponseDTO> challengeScrap(@PathVariable(name = "challengeId") Long challengeId){

        //임시 memberId
        Long memberId = 2L;
        JoinChallenge joinChallenge = joinChallengeCommandService.joinChallenge(memberId,challengeId);
        return ApiResponse.onSuccess(ChallengeConverter.toJoinChallengeResultDTO(joinChallenge));
    }

    @GetMapping("/{challengeId}")
    @Operation(summary = "챌린지 신청 페이지 조회 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO> getChallengePreview(@PathVariable(name = "challengeId") Long challengeId){

        Long memberId = 2L;

        ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO challengePreview = challengeQueryService.getChallengePreview(memberId,challengeId);

        return ApiResponse.onSuccess(challengePreview);

    }
}
