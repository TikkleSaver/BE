package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.service.Challenge.ChallengeCommandService;
import com.tikklesaver.domain.Challenge.service.Challenge.ChallengeQueryService;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeQueryService challengeQueryService;
    private final ChallengeCommandService challengeCommandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "챌린지 생성 API")
    public ApiResponse<ChallengeResponseDTO.challengeResultDTO> createChallenge(
            @CurrentMember Member member,
            @RequestPart("request") @Valid ChallengeRequestDTO.CreateChallengeDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {


        Challenge challenge = challengeCommandService.createChallenge(member.getId(), request, file);
        return ApiResponse.onSuccess(ChallengeConverter.toChallengeResultDTO(challenge));
    }

    @PatchMapping(value = "/{challengeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "챌린지 수정 API")
    public ApiResponse<ChallengeResponseDTO.challengeResultDTO> createChallenge(
            @CurrentMember Member member,
            @PathVariable("challengeId") Long challengeId,
            @RequestPart("request") @Valid ChallengeRequestDTO.CreateChallengeDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {


        Challenge challenge = challengeCommandService.updateChallenge(member.getId(),challengeId, request, file);
        return ApiResponse.onSuccess(ChallengeConverter.toChallengeResultDTO(challenge));
    }

    @DeleteMapping(value = "/{challengeId}")
    @Operation(summary = "챌린지 삭제 API")
    public ApiResponse<String> deleteChallenge(
            @CurrentMember Member member,
            @PathVariable("challengeId") Long challengeId) {

         challengeCommandService.deleteChallenge(member.getId(),challengeId);
        return ApiResponse.onSuccess("챌린지가 삭제되었습니다.");
    }


    @GetMapping("/lists")
    @Operation(summary = "카테고리별 챌린지 리스트 조회 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreViewListDTO> getChallengeList(@RequestParam(name = "category", required = false) Long categoryId, @RequestParam(name="page")Integer page){

        Long memberId = 1L;

        Page<Challenge> challengeList = challengeQueryService.getAllChallenges(memberId, categoryId, page);

        return ApiResponse.onSuccess(ChallengeConverter.challengePreViewListDTO(challengeList));

    }

    @GetMapping("/search")
    @Operation(summary = "카테고리별 챌린지 리스트 검색 API")
    public ApiResponse<ChallengeResponseDTO.ChallengePreViewListDTO> searchChallengeList(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "category", required = false) Long categoryId, @RequestParam(name="page")Integer page){

        Page<Challenge> challengeList = challengeQueryService.searchChallenges(keyword, categoryId, page);

        return ApiResponse.onSuccess(ChallengeConverter.challengePreViewListDTO(challengeList));

    }



}
