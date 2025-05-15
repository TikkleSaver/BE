package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.service.ChallengeQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {

    private final ChallengeQueryService challengeQueryService;

    @PostMapping
    @Operation(summary = "챌린지 생성 API")
    public ApiResponse<ChallengeResponseDTO.challengeResultDTO> createChallenge(
            @RequestBody @Valid ChallengeRequestDTO.CreateChallengeDTO request) {

        //임시 memberId
        Long memberId = 1L;
        Challenge challenge = challengeQueryService.createChallenge(memberId, request);
        return ApiResponse.onSuccess(ChallengeConverter.toChallengeResultDTO(challenge));
    }
}
