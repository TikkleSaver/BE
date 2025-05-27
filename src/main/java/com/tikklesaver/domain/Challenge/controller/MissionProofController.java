package com.tikklesaver.domain.Challenge.controller;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofRequestDTO;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.Challenge.service.MissionProof.MissionProofCommandService;
import com.tikklesaver.domain.Challenge.service.MissionProof.MissionProofQueryService;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission-proof")
public class MissionProofController {


    private final MissionProofCommandService missionProofCommandService;
    private final MissionProofQueryService missionProofQueryService;

    @PostMapping(value = "/{challengeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "챌린지 미션 수행 API")
    public ApiResponse<MissionProofResponseDTO.missionProofResultDTO> createMissionProof(
            @CurrentMember Member member,
            @PathVariable(name = "challengeId") Long challengeId,
            @RequestPart("request") @Valid MissionProofRequestDTO.CreateMissionDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {


        MissionProof missionProof = missionProofCommandService.createMissionProof(member,challengeId,request,file);

        return ApiResponse.onSuccess(ChallengeConverter.toMissionProofResultDTO(missionProof));
    }

    @PatchMapping( value ="/{missionProofId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "미션 수행 수정 API")
    public ApiResponse<MissionProofResponseDTO.missionProofResultDTO> updateMissionProof(
            @CurrentMember Member member,
            @PathVariable(name = "missionProofId") Long missionProofId,
            @RequestPart("request") @Valid MissionProofRequestDTO.CreateMissionDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        MissionProof missionProof = missionProofCommandService.updateMissionProof(member,missionProofId,request,file);

        return ApiResponse.onSuccess(ChallengeConverter.toMissionProofResultDTO(missionProof));
    }

    @DeleteMapping("/{missionProofId}")
    @Operation(summary = "미션 인증 삭제 API")
    public ApiResponse<?> deleteMissionProof(
            @CurrentMember Member member,
            @PathVariable Long missionProofId) {

        missionProofCommandService.deleteMissionProof(member, missionProofId);
        return ApiResponse.onSuccess("미션 인증이 삭제되었습니다.");
    }

    @GetMapping("/{challengeId}/month")
    public ApiResponse<List<MissionProofResponseDTO.missionProofResultDTO>> getMonthlyProofs(
            @CurrentMember Member member,
            @RequestParam int year,
            @RequestParam int month,
            @PathVariable("challengeId") Long challengeId
    ) {
        List<MissionProofResponseDTO.missionProofResultDTO> result = missionProofQueryService.getMissionProofsByMonth(member.getId(), challengeId, year, month);
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/{challengeId}/main")
    public ApiResponse<MissionProofResponseDTO.missionProofMainDTO> getMonthlyProofs(
            @CurrentMember Member member,
            @PathVariable("challengeId") Long challengeId
    ) {
        MissionProofResponseDTO.missionProofMainDTO result = missionProofQueryService.getMissionProofMain(member,challengeId);
        return ApiResponse.onSuccess(result);
    }
}
