package com.tikklesaver.domain.wish.controller;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.product.service.ProductCommandService;
import com.tikklesaver.domain.wish.converter.WishConverter;
import com.tikklesaver.domain.wish.dto.wish.WishRequestDTO;
import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.SatisfactionStatus;
import com.tikklesaver.domain.wish.service.wish.WishQueryService;
import com.tikklesaver.domain.wish.service.wish.WishCommandService;
import com.tikklesaver.global.annotation.CurrentMember;
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
            @CurrentMember Member member,
            @RequestBody @Valid WishRequestDTO.CreateWishFromExistingProductDTO request) {

        Product newProduct = productCommandService.createExistingProduct(member, request);
        Wish newWish = wishCommandService.createWishFromExistingProduct(member, newProduct, request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(newWish));
    }

    // 존재하지 않는 상품 위시로 추가 (직접 추가한 상품)
    @PostMapping(value =  "/my-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "원하는 상품 직접 추가해서 위시로 생성 API")
    public ApiResponse<WishResponseDTO.WishResultDTO> createWishFromMyProduct(
            @CurrentMember Member member,
            @RequestPart("request") @Valid WishRequestDTO.CreateWishFromMyProductDTO request,
            @RequestPart("file") MultipartFile file) {

        Product newProduct = productCommandService.createMyProduct(member, request, file);
        Wish newWish = wishCommandService.createWishFromMyProduct(member, newProduct, request);

        return ApiResponse.onSuccess(WishConverter.toWishResultDTO(newWish));
    }

    // 존재하는 상품 위시 수정
    @PatchMapping("/{wishId}/existing-product")
    @Operation(summary = "이미 존재하는 상품의 위시 내용 수정 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishFromExistingProduct(
            @CurrentMember Member member,
            @PathVariable(name = "wishId") Long wishId,
            @RequestBody @Valid WishRequestDTO.UpdateWishFromExistingProductDTO request) {

        Wish wish = wishCommandService.updateWishFromExistingProduct(member, wishId, request);
        productCommandService.updateExistingProduct(member, wish.getProduct(), request);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

    // 존재하지 않는 상품 위시 수정 (직접 추가한 상품)
    @PatchMapping(value = "/{wishId}/my-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "원하는 상품 직접 추가했던 위시 내용 수정 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishFromMyProduct(
            @CurrentMember Member member,
            @PathVariable(name = "wishId") Long wishId,
            @RequestPart("request") @Valid WishRequestDTO.UpdateWishFromMyProductDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Wish wish = wishCommandService.updateWishFromMyProduct(member, wishId, request);
        productCommandService.updateMyProduct(member, wish.getProduct(), request, file);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

    // 상품 위시 삭제
    @DeleteMapping("/{wishId}")
    @Operation(summary = "상품의 위시 삭제 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<String> deleteWish(
            @CurrentMember Member member,
            @PathVariable(name = "wishId") Long wishId) {

        Wish wish = wishCommandService.deleteWish(member, wishId);
        productCommandService.deleteProduct(member, wish.getProduct());

        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    // 위시리스트 상세 조회
    @GetMapping("/{wishId}")
    @Operation(summary = "위시 상세 조회 API")
    public ApiResponse<WishResponseDTO.WishDetailDTO> getWishDetail(
            @PathVariable(name = "wishId") Long wishId) {

        return ApiResponse.onSuccess(wishQueryService.getWishDetail(wishId));
    }

    // 나의 위시리스트 구매예정 목록 조회
    @GetMapping("/mine/planned")
    @Operation(summary = "나의 위시리스트 구매예정 목록 조회 API")
    public ApiResponse<WishResponseDTO.MyWishPlannedPreviewListDTO> getMyWishPlanned(@CurrentMember Member member) {

        List<WishResponseDTO.MyWishPlannedPreviewDTO> myWishPlannedList = wishQueryService.getMyWishPlannedList(member);
        return ApiResponse.onSuccess(WishConverter.myWishPlannedPreviewListDTO(myWishPlannedList));
    }

    // 나의 위시리스트 구매완료 목록 조회
    @GetMapping("/mine/purchased")
    @Operation(summary = "나의 위시리스트 구매완료 목록 조회 API")
    public ApiResponse<WishResponseDTO.MyWishPurchasedPreviewListDTO> getMyWishPurchased(@CurrentMember Member member) {

        List<WishResponseDTO.MyWishPurchasedPreviewDTO> myWishPurchasedList = wishQueryService.getMyWishPurchasedList(member);
        return ApiResponse.onSuccess(WishConverter.myWishPurchasedPreviewListDTO(myWishPurchasedList));
    }


    // 친구의 위시리스트 구매예정 목록 조회
    @GetMapping("/friend/{friendId}/planned")
    @Operation(summary = "친구의 위시리스트 구매예정 목록 조회 API")
    @Parameters({
            @Parameter(name = "friendId", description = "친구의 MEMBER_ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.FriendWishPlannedPreviewListDTO> getFriendWishPlanned(
            @CurrentMember Member member,
            @PathVariable(name = "friendId") Long friendId) {

        List<WishResponseDTO.FriendWishPlannedPreviewDTO> friendWishPlannedList = wishQueryService.getFriendWishPlannedList(member, friendId);
        return ApiResponse.onSuccess(WishConverter.friendWishPlannedPreviewListDTO(friendWishPlannedList));
    }

    // 친구의 위시리스트 구매완료 목록 조회
    @GetMapping("/friend/{friendId}/purchased")
    @Operation(summary = "친구의 위시리스트 구매완료 목록 조회 API")
    @Parameters({
            @Parameter(name = "friendId", description = "친구의 MEMBER_ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.FriendWishPurchasedPreviewListDTO> getFriendWishPurchased(
            @CurrentMember Member member,
            @PathVariable(name = "friendId") Long friendId) {

        List<WishResponseDTO.FriendWishPurchasedPreviewDTO> friendWishPurchasedList = wishQueryService.getFriendWishPurchasedList(member, friendId);
        return ApiResponse.onSuccess(WishConverter.friendWishPurchasedPreviewListDTO(friendWishPurchasedList));
    }

    // 나의 위시 공개/비공개 설정
    @PatchMapping( "/{wishId}/public-status")
    @Operation(summary = "나의 위시 공개면 비공개, 비공개면 공개로 변경 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishPublicStatus(
            @CurrentMember Member member,
            @PathVariable(name = "wishId") Long wishId) {

        Wish wish = wishCommandService.updateWishPublicStatus(member, wishId);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

    // 나의 위시 구매 설정
    @PatchMapping( "/{wishId}/purchase-status")
    @Operation(summary = "나의 위시 구매 상태로 변경 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishPurchaseStatus(
            @CurrentMember Member member,
            @PathVariable(name = "wishId") Long wishId) {

        Wish wish = wishCommandService.updateWishPurchaseStatus(member, wishId);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

    // 나의 위시 만족/불만족 설정
    @PatchMapping( "/{wishId}/satisfaction-status")
    @Operation(summary = "나의 위시 만족/불만족 수정 API")
    @Parameters({
            @Parameter(name = "wishId", description = "위시의 ID, path variable 입니다!"),
            @Parameter(name = "status", description = "만족 상태 (SATISFIED = 만족, DISSATISFIED = 불만족)")
    })
    public ApiResponse<WishResponseDTO.UpdateWishResultDTO> updateWishSatisfactionStatus(
            @CurrentMember Member member,
            @PathVariable(name = "wishId") Long wishId,
            @RequestParam(name = "status") SatisfactionStatus status) {

        Wish wish = wishCommandService.updateWishSatisfactionStatus(member, wishId, status);

        return ApiResponse.onSuccess(WishConverter.toWishUpdateResultDTO(wish));
    }

}
