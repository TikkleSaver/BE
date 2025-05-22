package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.dto.challengeScrap.ChallengeScrapResponseDTO;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.Challenge.service.ChallengeScrap.ChallengeScrapCommandService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges-scrap")
public class ChallengeScrapController {

    private final ChallengeScrapCommandService challengeScrapCommandService;

    @PatchMapping("/{challengeId}/scrap")
    @Operation(summary = "챌린지 스크랩 추가/삭제 API")
    public ApiResponse<ChallengeScrapResponseDTO> joinChallenge(@PathVariable(name = "challengeId") Long challengeId){

        //임시 memberId
        Long memberId = 2L;
        ChallengeScrapResponseDTO challengeScrap = challengeScrapCommandService.changeScrap(challengeId,memberId);
        return ApiResponse.onSuccess(challengeScrap);
    }

}
