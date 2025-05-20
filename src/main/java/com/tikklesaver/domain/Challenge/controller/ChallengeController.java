package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.service.Challenge.ChallengeCommandService;
import com.tikklesaver.domain.Challenge.service.Challenge.ChallengeQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeQueryService challengeQueryService;
    private final ChallengeCommandService challengeCommandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "챌린지 생성 API")
    public ApiResponse<ChallengeResponseDTO.challengeResultDTO> createChallenge(
            @RequestPart("request") @Valid ChallengeRequestDTO.CreateChallengeDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        //임시 memberId
        Long memberId = 1L;
        Challenge challenge = challengeCommandService.createChallenge(memberId, request, file);
        return ApiResponse.onSuccess(ChallengeConverter.toChallengeResultDTO(challenge));
    }


    @GetMapping("/lists")
    @Operation(summary = "카테고리별 챌린지 리스트 조회 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreViewListDTO> getChallengeList(@RequestParam(name = "category", required = false) Long categoryId, @RequestParam(name="page")Integer page){

        Long memberId = 1L;

        Page<Challenge> challengeList = challengeQueryService.getAllChallenges(memberId, categoryId, page);

        return ApiResponse.onSuccess(ChallengeConverter.challengePreViewListDTO(challengeList));

    }

    @GetMapping("/join-challenge/{challengeId}")
    @Operation(summary = "챌린지 신청 페이지 조회 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO> getChallengePreview(@PathVariable(name = "challengeId") Long challengeId){

        Long memberId = 2L;

        ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO challengePreview = challengeQueryService.getChallengePreview(memberId,challengeId);

        return ApiResponse.onSuccess(challengePreview);

    }


}
