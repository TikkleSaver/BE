package com.tikklesaver.domain.wish.dto;

import com.tikklesaver.domain.wish.entity.enums.PublicStatus;
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
}
