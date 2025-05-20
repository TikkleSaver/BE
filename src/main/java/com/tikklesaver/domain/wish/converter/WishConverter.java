package com.tikklesaver.domain.wish.converter;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.dto.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.ProductType;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WishConverter {

    // 위시 생성
    public static Wish toWish(Member member, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request){
        return Wish.builder()
                .purchaseStatus(PurchaseStatus.PLANNED)
                .publicStatus(request.getPublicStatus())
                .productType(ProductType.PRODUCT)
                .member(member)
                .product(product)
                .build();
    }

    // 위시 결과
    public static WishResponseDTO.WishResultDTO toWishResultDTO(Wish wish){
        return WishResponseDTO.WishResultDTO.builder()
                .wishId(wish.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
