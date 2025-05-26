package com.tikklesaver.domain.wish.controller;

import com.tikklesaver.domain.wish.converter.WishCommentConverter;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentRequestDTO;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentResponseDTO;
import com.tikklesaver.domain.wish.entity.WishComment;
import com.tikklesaver.domain.wish.service.wishComment.WishCommentCommandService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishCommentContoller {

    private final WishCommentCommandService wishCommentCommandService;

    // 위시 댓글 생성
    @PostMapping("/{wishId}/comment")
    @Operation(summary = "이미 존재하는 상품을 위시로 생성 API")
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
}
