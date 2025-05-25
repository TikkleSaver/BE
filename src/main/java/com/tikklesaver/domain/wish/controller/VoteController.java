package com.tikklesaver.domain.wish.controller;

import com.tikklesaver.domain.wish.converter.VoteConverter;
import com.tikklesaver.domain.wish.dto.vote.VoteResponseDTO;
import com.tikklesaver.domain.wish.entity.Vote;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;
import com.tikklesaver.domain.wish.service.vote.VoteCommandService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class VoteController {

    private final VoteCommandService voteCommandService;

    // 위시 찬성/반대 투표
    @PostMapping( "/{wishId}/vote")
    @Operation(summary = "위시 찬성/반대 투표 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!"),
            @Parameter(name = "status", description = "찬반 상태 (LIKE = 찬성, UNLIKE = 반대)")
    })
    public ApiResponse<VoteResponseDTO.VoteResultDTO> voteOnWish(
            @PathVariable(name = "wishId") Long wishId,
            @RequestParam(name = "status") LikeStatus status) {

        //임시 memberId
        Long memberId = 5L;

        Vote vote = voteCommandService.voteOnWish(memberId, wishId, status);

        return ApiResponse.onSuccess(VoteConverter.toVoteResultDTO(vote));
    }

    // 위시 찬성/반대 투표 취소
    @DeleteMapping( "/{wishId}/vote")
    @Operation(summary = "위시 찬성/반대 투표 취소 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<String> cancelVote(
            @PathVariable(name = "wishId") Long wishId) {

        //임시 memberId
        Long memberId = 5L;

        voteCommandService.cancelVote(memberId, wishId);

        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }
}
