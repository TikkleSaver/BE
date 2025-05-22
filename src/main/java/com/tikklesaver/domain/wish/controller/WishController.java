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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        Wish newWish = wishCommandService.createWishFromExistingProduct(memberId, newProduct, request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(newWish));
    }

    // 존재하지 않는 상품 위시로 추가 (직접 추가한 상품)
    @PostMapping(value =  "/my-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "원하는 상품 직접 추가해서 위시로 생성 API")
    public ApiResponse<WishResponseDTO.WishResultDTO> createWishFromMyProduct(
            @RequestPart("request") @Valid WishRequestDTO.CreateWishFromMyProductDTO request,
            @RequestPart("file") MultipartFile file) {

        //임시 memberId
        Long memberId = 5L;

        Product newProduct = productCommandService.createMyProduct(memberId, request, file);
        Wish newWish = wishCommandService.createWishFromMyProduct(memberId, newProduct, request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(newWish));
    }

    // 존재하는 상품 위시 수정
    @PatchMapping("/{wishId}/existing-product")
    @Operation(summary = "이미 존재하는 상품의 위시 내용 수정 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishFromExistingProduct(
            @PathVariable(name = "wishId") Long wishId,
            @RequestBody @Valid WishRequestDTO.UpdateWishFromExistingProductDTO request) {

        //임시 memberId
        Long memberId = 5L;

        Wish wish = wishCommandService.updateWishFromExistingProduct(memberId, wishId, request);
        productCommandService.updateExistingProduct(memberId, wish.getProduct(), request);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

    // 존재하지 않는 상품 위시 수정 (직접 추가한 상품)
    @PatchMapping(value = "/{wishId}/my-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "원하는 상품 직접 추가했던 위시 내용 수정 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishFromMyProduct(
            @PathVariable(name = "wishId") Long wishId,
            @RequestPart("request") @Valid WishRequestDTO.UpdateWishFromMyProductDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        //임시 memberId
        Long memberId = 5L;

        Wish wish = wishCommandService.updateWishFromMyProduct(memberId, wishId, request);
        productCommandService.updateMyProduct(memberId, wish.getProduct(), request, file);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

    // 존재하는 상품 위시 삭제
    @DeleteMapping("/{wishId}/existing-product")
    @Operation(summary = "이미 존재하는 상품의 위시 삭제 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<String> deleteWishFromExistingProduct(
            @PathVariable(name = "wishId") Long wishId) {

        //임시 memberId
        Long memberId = 5L;

        Wish wish = wishCommandService.deleteWishFromExistingProduct(memberId, wishId);
        productCommandService.deleteExistingProduct(memberId, wish.getProduct());

        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    // 존재하지 않는 상품 위시 삭제 (직접 추가한 상품)
    @DeleteMapping("/{wishId}/my-product")
    @Operation(summary = "원하는 상품 직접 추가했던 상품의 위시 삭제 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<String> deleteWishFromMyProduct(
            @PathVariable(name = "wishId") Long wishId) {

        //임시 memberId
        Long memberId = 5L;

        Wish wish = wishCommandService.deleteWishFromMyProduct(memberId, wishId);
        productCommandService.deleteMyProduct(memberId, wish.getProduct());

        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    // 위시리스트 상세 조회
    @GetMapping("/{wishId}")
    @Operation(summary = "위시 상세 조회 API")
    public ApiResponse<WishResponseDTO.WishDetailDTO> getWishDetail(@PathVariable(name = "wishId") Long wishId) {

        //임시 memberId
        Long memberId = 5L;

        return ApiResponse.onSuccess(wishQueryService.getWishDetail(wishId));
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
