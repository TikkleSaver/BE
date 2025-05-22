package com.tikklesaver.domain.wish.service;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;

public interface WishCommandService {
    
    // 존재하는 상품 위시에 추가
    Wish createWishFromExistingProduct(Long memberId, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request);

    // 존재하지 않는 상품 위시에 추가
    Wish createWishFromMyProduct(Long memberId, Product product, WishRequestDTO.CreateWishFromMyProductDTO request);

    // 존재하는 상품 위시 수정
    Wish updateWishFromExistingProduct(Long memberId, Long wishId, WishRequestDTO.UpdateWishFromExistingProductDTO request);


    // 존재하는 상품 위시 삭제
    Wish deleteWishFromExistingProduct(Long memberId, Long wishId);
}
