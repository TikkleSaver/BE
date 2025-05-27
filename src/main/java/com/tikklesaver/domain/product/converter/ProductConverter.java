package com.tikklesaver.domain.product.converter;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.wish.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.enums.ProductType;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    // 상품 생성
    public static Product toProduct(Member member, WishRequestDTO.CreateWishFromExistingProductDTO request, Category category){
        return Product.builder()
                .productType(ProductType.PRODUCT)
                .title(request.getTitle())
                .brand(request.getBrand())
                .price(request.getPrice())
                .image(request.getImage())
                .category1(request.getCategory1())
                .category2(request.getCategory2())
                .category3(request.getCategory3())
                .category4(request.getCategory4())
                .category(category)
                .member(member)
                .build();
    }

    // 존재하지 않는 상품 생성
    public static Product toMyProduct(Member member, WishRequestDTO.CreateWishFromMyProductDTO request, Category category, String imageUrl){
        return Product.builder()
                .productType(ProductType.MYPRODUCT)
                .title(request.getTitle())
                .brand(request.getBrand())
                .price(request.getPrice())
                .image(imageUrl)
                .category(category)
                .member(member)
                .build();
    }
}
