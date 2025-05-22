package com.tikklesaver.domain.product.service;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProductCommandService {

    // 존재하는 상품 추가
    Product createExistingProduct(Long memberId, WishRequestDTO.CreateWishFromExistingProductDTO request);

    // 존재하지 않는 상품 추가
    Product createMyProduct(Long memberId, WishRequestDTO.CreateWishFromMyProductDTO request, MultipartFile file);

    // 존재하는 상품 수정
    Product updateExistingProduct(Long memberId, Product product, WishRequestDTO.UpdateWishFromExistingProductDTO request);

    // 존재하지 않는 상품 수정 (직접 추가한 상품)
    Product updateMyProduct(Long memberId, Product product, WishRequestDTO.UpdateWishFromMyProductDTO request, MultipartFile file);

    // 존재하는 상품 삭제
    void deleteExistingProduct(Long memberId, Product product);
}
