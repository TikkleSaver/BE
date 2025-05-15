package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.service.ChallengeQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    @GetMapping("/lists")
    @Operation(summary = "카테고리별 게시글 리스트 조회 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreViewListDTO> getChallengeList(@RequestParam(name = "category", required = false) Long categoryId, @RequestParam(name="page")Integer page){

        Long memberId = 1L;

        Page<Challenge> challengeList = challengeQueryService.getAllChallenges(memberId, categoryId, page);

        return ApiResponse.onSuccess(ChallengeConverter.challengePreViewListDTO(challengeList));

    }
}
