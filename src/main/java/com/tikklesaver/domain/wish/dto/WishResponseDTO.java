package com.tikklesaver.domain.wish.dto;

import com.tikklesaver.domain.wish.entity.enums.ProductType;
import com.tikklesaver.domain.wish.entity.enums.PublicStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class WishResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishResultDTO {
        Long wishId;
        LocalDateTime createdAt;
    }

    // 나의 위시리스트 구매 예정 목록 조회 (목록용)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyWishPlannedPreviewListDTO {

        List<MyWishPlannedPreviewDTO> myWishPlannedLst;
    }

    // 나의 위시리스트 구매 예정 목록 조회 (세부 상자)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyWishPlannedPreviewDTO {
        Long wishId;
        Long memberId;
        String nickname;
//        String profileImg;
        String title;
        Integer price;
        String image;
        PublicStatus publicStatus;
        ProductType productType;
        Long likeCnt;
        Long unLikeCnt;
        Long  commentCnt;
        LocalDateTime createdAt;
    }
}
