package com.tikklesaver.domain.product.entity;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.enums.ProductType;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 타입 구분
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private ProductType productType;

    // 상품명
    @Column(nullable = false)
    private String title;
    
    // 브랜드
    @Column(nullable = false)
    private String brand;
    
    // 가격
    @Column(nullable = false)
    private Integer price;
    
    // 섬네일
    @Column(nullable = false)
    private String image;
    
    // 대분류
    @Column(nullable = false)
    private String category1;
    
    // 중분류
    private String category2;
    
    // 소분류
    private String category3;
    
    // 세분류
    private String category4;

    // 작성자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 카테고리 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
