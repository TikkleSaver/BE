package com.tikklesaver.domain.product.service;

import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;

public interface ProductCommandService {

    Product createProduct(Long memberId, WishRequestDTO.CreateWishFromExistingProductDTO request);
}
