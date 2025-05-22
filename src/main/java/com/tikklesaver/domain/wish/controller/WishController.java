package com.tikklesaver.domain.wish.controller;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.product.service.ProductCommandService;
import com.tikklesaver.domain.wish.converter.WishConverter;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.dto.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.service.WishQueryService;
import com.tikklesaver.domain.wish.service.WishCommandService;
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
public class WishController {

    private final WishCommandService wishCommandService;
    private final WishQueryService wishQueryService;
    private final ProductCommandService productCommandService;

    // 존재하는 상품 위시로 추가
    @PostMapping("/existing-product")
    @Operation(summary = "이미 존재하는 상품을 위시로 생성 API")
    public ApiResponse<WishResponseDTO.WishResultDTO> createWishFromExistingProduct(
            @RequestBody @Valid WishRequestDTO.CreateWishFromExistingProductDTO request) {

        //임시 memberId
        Long memberId = 5L;

        Product newProduct = productCommandService.createExistingProduct(memberId, request);
        Wish newWish = wishCommandService.CreateWishFromExistingProduct(memberId, newProduct, request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(newWish));
    }

    // 존재하는 상품 위시 수정
    @PatchMapping("/{wishId}/existing-product")
    @Operation(summary = "이미 존재하는 상품의 위시 내용 수정 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.WishResultDTO> updateWishFromExistingProduct(
            @PathVariable(name = "wishId") Long wishId,
            @RequestBody @Valid WishRequestDTO.UpdateWishFromExistingProductDTO request) {

        //임시 memberId
        Long memberId = 5L;

        Wish wish = wishCommandService.UpdateWishFromExistingProduct(memberId, wishId, request);
        productCommandService.updateExistingProduct(memberId, wish.getProduct(), request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(wish));
    }

    // 나의 위시리스트 구매예정 목록 조회
    @GetMapping("/mine/planned")
    @Operation(summary = "나의 위시리스트 구매예정 목록 조회 API")
    public ApiResponse<WishResponseDTO.MyWishPlannedPreviewListDTO> getMyWishPlanned() {

        //임시 memberId
        Long memberId = 5L;

        List<WishResponseDTO.MyWishPlannedPreviewDTO> myWishPlannedList = wishQueryService.getMyWishPlannedList(memberId);
        return ApiResponse.onSuccess(WishConverter.myWishPlannedPreviewListDTO(myWishPlannedList));
    }

    // 나의 위시리스트 구매완료 목록 조회
    @GetMapping("/mine/purchased")
    @Operation(summary = "나의 위시리스트 구매완료 목록 조회 API")
    public ApiResponse<WishResponseDTO.MyWishPurchasedPreviewListDTO> getMyWishPurchased() {

        //임시 memberId
        Long memberId = 5L;

        List<WishResponseDTO.MyWishPurchasedPreviewDTO> myWishPurchasedList = wishQueryService.getMyWishPurchasedList(memberId);
        return ApiResponse.onSuccess(WishConverter.myWishPurchasedPreviewListDTO(myWishPurchasedList));
    }
}
