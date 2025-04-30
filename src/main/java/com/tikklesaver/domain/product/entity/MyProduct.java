package com.tikklesaver.domain.product.entity;

import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class MyProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // 회원 ID (FK)

    // 카테고리 ID (FK)

}
