package com.tikklesaver.domain.wish.service.wish;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.wish.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.SatisfactionStatus;

public interface WishCommandService {
    
    // 존재하는 상품 위시에 추가
    Wish createWishFromExistingProduct(Member member, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request);

    // 존재하지 않는 상품 위시에 추가 (직접 추가한 상품)
    Wish createWishFromMyProduct(Member member, Product product, WishRequestDTO.CreateWishFromMyProductDTO request);

    // 존재하는 상품 위시 수정
    Wish updateWishFromExistingProduct(Member member, Long wishId, WishRequestDTO.UpdateWishFromExistingProductDTO request);

    // 존재하지 않는 상품 위시 수정 (직접 추가한 상품)
    Wish updateWishFromMyProduct(Member member, Long wishId, WishRequestDTO.UpdateWishFromMyProductDTO request);

    // 상품 위시 삭제
    Wish deleteWish(Member member, Long wishId);

    // 나의 위시 공개/비공개 설정
    Wish updateWishPublicStatus(Member member, Long wishId);

    // 나의 위시 구매 설정
    Wish updateWishPurchaseStatus(Member member, Long wishId);

    // 나의 위시 만족/불만족 설정
    Wish updateWishSatisfactionStatus(Member member, Long wishId, SatisfactionStatus status);
}
