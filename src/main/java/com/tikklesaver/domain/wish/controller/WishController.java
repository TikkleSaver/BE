package com.tikklesaver.domain.wish.controller;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.product.service.ProductCommandService;
import com.tikklesaver.domain.wish.converter.WishConverter;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.dto.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.service.WishCommandService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishController {

    private final WishCommandService wishCommandService;
    private final ProductCommandService productCommandService;

    // 존재하는 상품 위시로 추가
    @PostMapping("/existing-product")
    @Operation(summary = "이미 존재하는 상품을 위시로 생성 API")
    public ApiResponse<WishResponseDTO.WishResultDTO> createWishFromExistingProduct(
            @RequestBody @Valid WishRequestDTO.CreateWishFromExistingProductDTO request) {

        //임시 memberId
        Long memberId = 5L;

        Product newProduct = productCommandService.createProduct(memberId, request);
        Wish newWish = wishCommandService.CreateWishFromExistingProduct(memberId, newProduct, request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(newWish));
    }
}
