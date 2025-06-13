package com.tikklesaver.domain.wish.dto.wish;

import com.tikklesaver.domain.wish.entity.enums.PublicStatus;
import com.tikklesaver.domain.wish.entity.enums.SatisfactionStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class WishRequestDTO {

    // 존재하는 상품 위시로 추가
    @Getter
    public static class CreateWishFromExistingProductDTO {

        // 위시 관련
        @NotNull
        PublicStatus publicStatus;

        // 상품 관련
        @NotEmpty
        String title;

        @NotEmpty
        String brand;

        @NotNull
        Integer price;

        @NotEmpty
        String image;

        @NotEmpty
        String category1;

        String category2;

        String category3;

        String category4;

        @NotNull
        Long categoryId;
    }

    // 존재하지 않는 상품 위시로 추가
    @Getter
    public static class CreateWishFromMyProductDTO {

        // 위시 관련
        @NotNull
        PublicStatus publicStatus;

        // 상품 관련
        @NotEmpty
        String title;

        @NotEmpty
        String brand;

        @NotNull
        Integer price;

        @NotNull
        Long categoryId;
    }

    // 존재하는 상품 위시 수정
    @Getter
    public static class UpdateWishFromExistingProductDTO {

        @NotNull
        PublicStatus publicStatus;

        SatisfactionStatus satisfactionStatus;

        @NotNull
        Integer price;

        @NotNull
        Long categoryId;
    }

    // 존재하지 않는 상품 위시 수정 (직접 추가한 상품)
    @Getter
    public static class UpdateWishFromMyProductDTO {

        @NotNull
        PublicStatus publicStatus;

        SatisfactionStatus satisfactionStatus;

        String title;

        String brand;

        @NotNull
        Integer price;

        @NotNull
        Long categoryId;
    }
}
