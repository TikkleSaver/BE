package com.tikklesaver.domain.wish.dto.wishComment;

import com.tikklesaver.domain.wish.entity.enums.ProductType;
import com.tikklesaver.domain.wish.entity.enums.PublicStatus;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
import com.tikklesaver.domain.wish.entity.enums.SatisfactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class WishCommentResponseDTO {

    // 위시 댓글 생성 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishCommentResultDTO {
        Long wishCommentId;
        LocalDateTime createdAt;
    }

    // 위시 댓글 수정 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateWishCommentResultDTO {
        Long wishCommentId;
        LocalDateTime updatedAt;
    }
}
