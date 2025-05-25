package com.tikklesaver.domain.wish.dto.wish;

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

public class WishResponseDTO {

    // 위시 생성 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishResultDTO {
        Long wishId;
        LocalDateTime createdAt;
    }

    // 위시 수정 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateWishResultDTO {
        Long wishId;
        LocalDateTime updatedAt;
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


    // 위시 상세 정보 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishDetailDTO {
        Long wishId;
        Long memberId;
        String nickname;
        //        String profileImg;
        Long categoryId;
        String title;
        String brand;
        Integer price;
        String productImg;
        PublicStatus publicStatus;
        SatisfactionStatus satisfactionStatus;
        PurchaseStatus purchaseStatus;
        ProductType productType;
        Long likeCnt;
        Long unLikeCnt;
        Long  commentCnt;
        LocalDateTime createdAt;
    }

    // 나의 위시리스트 구매 완료 목록 조회 (목록용)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyWishPurchasedPreviewListDTO {

        List<MyWishPurchasedPreviewDTO> myWishPurchasedLst;
    }

    // 나의 위시리스트 구매 완료 목록 조회 (세부 상자)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyWishPurchasedPreviewDTO {
        Long wishId;
        Long memberId;
        String nickname;
        //        String profileImg;
        String title;
        Integer price;
        String image;
        PublicStatus publicStatus;
        SatisfactionStatus satisfactionStatus;
        ProductType productType;
        Long likeCnt;
        Long unLikeCnt;
        Long  commentCnt;
        LocalDateTime createdAt;
    }
}
