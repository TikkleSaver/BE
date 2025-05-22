package com.tikklesaver.domain.product.service;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;

public interface ProductCommandService {

    // 존재하는 상품 추가
    Product createExistingProduct(Long memberId, WishRequestDTO.CreateWishFromExistingProductDTO request);

    // 존재하는 상품 수정
    Product updateExistingProduct(Long memberId, Product product, WishRequestDTO.UpdateWishFromExistingProductDTO request);


    // 존재하는 상품 삭제
    void deleteExistingProduct(Long memberId, Product product);
}
