package com.tikklesaver.domain.wish.controller;

import com.tikklesaver.domain.wish.converter.WishCommentConverter;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentRequestDTO;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentResponseDTO;
import com.tikklesaver.domain.wish.entity.WishComment;
import com.tikklesaver.domain.wish.service.wishComment.WishCommentCommandService;
import com.tikklesaver.domain.wish.service.wishComment.WishCommentQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishCommentContoller {

    private final WishCommentCommandService wishCommentCommandService;
    private final WishCommentQueryService wishCommentQueryService;

    // 위시 댓글 생성
    @PostMapping("/{wishId}/comment")
    @Operation(summary = "위시 댓글 생성 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishCommentResponseDTO.WishCommentResultDTO> createWishComment(
            @PathVariable(name = "wishId") Long wishId,
            @RequestBody @Valid WishCommentRequestDTO.CreateWishCommentDTO request) {

        //임시 memberId
        Long memberId = 5L;

        WishComment newWishComment = wishCommentCommandService.createWishComment(memberId, wishId, request);

        return ApiResponse.onSuccess(WishCommentConverter.toWishCommentResultDTO(newWishComment));
    }

    // 위시 댓글 수정
    @PatchMapping("/comment/{commentId}")
    @Operation(summary = "위시 댓글 수정 API")
    @Parameters({
            @Parameter(name = "commentId", description = "위시 댓글의 ID, path variable 입니다!")
    })
    public ApiResponse<WishCommentResponseDTO.UpdateWishCommentResultDTO> updateWishComment(
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody @Valid WishCommentRequestDTO.UpdateWishCommentDTO request) {

        //임시 memberId
        Long memberId = 5L;

        WishComment wishComment = wishCommentCommandService.updateWishComment(memberId, commentId, request);

        return ApiResponse.onSuccess(WishCommentConverter.toUpdateWishCommentResultDTO(wishComment));
    }

    // 위시 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "위시 댓글 삭제 API")
    @Parameters({
            @Parameter(name = "commentId", description = "위시 댓글의 ID, path variable 입니다!")
    })
    public ApiResponse<String> deleteWishComment(
            @PathVariable(name = "commentId") Long commentId) {

        //임시 memberId
        Long memberId = 5L;

         wishCommentCommandService.deleteWishComment(memberId, commentId);

        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    // 위시 댓글 목록 조회
    @GetMapping("/{wishId}/comment")
    @Operation(summary = "위시 댓글 목록 조회 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishCommentResponseDTO.WishCommentPreviewListDTO> getWishCommentList(
            @PathVariable(name = "wishId") Long wishId) {

        //임시 memberId
        Long memberId = 5L;

        List<WishCommentResponseDTO.WishCommentPreviewDTO> wishCommentList = wishCommentQueryService.getWishCommentList(memberId, wishId);

        return ApiResponse.onSuccess(WishCommentConverter.toGetWishCommentListResultDTO(wishCommentList));
    }
}
