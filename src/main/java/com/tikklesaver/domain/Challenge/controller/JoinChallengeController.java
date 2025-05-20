package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.joinChallenge.JoinChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.service.JoinChallenge.JoinChallengeCommandService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-challenges")
public class JoinChallengeController {

    private final JoinChallengeCommandService joinChallengeCommandService;

    @PostMapping("/{challengeId}")
    @Operation(summary = "챌린지 참여 요청 API")
    public ApiResponse<JoinChallengeResponseDTO> challengeScrap(@PathVariable(name = "challengeId") Long challengeId){

        //임시 memberId
        Long memberId = 2L;
        JoinChallenge joinChallenge = joinChallengeCommandService.joinChallenge(memberId,challengeId);
        return ApiResponse.onSuccess(ChallengeConverter.toJoinChallengeResultDTO(joinChallenge));
    }
}
