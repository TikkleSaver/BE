package com.tikklesaver.domain.wish.service;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;

public interface WishCommandService {
    
    // 존재하는 상품 위시에 추가
    Wish CreateWishFromExistingProduct(Long memberId, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request);
}
