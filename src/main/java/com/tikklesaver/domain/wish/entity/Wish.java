package com.tikklesaver.domain.wish.entity;

import com.tikklesaver.domain.wish.enums.ProductType;
import com.tikklesaver.domain.wish.enums.PublicStatus;
import com.tikklesaver.domain.wish.enums.PurchaseStatus;
import com.tikklesaver.domain.wish.enums.SatisfactionStatus;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Wish extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매 상태
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private PurchaseStatus purchaseStatus;

    // 만족 상태
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private SatisfactionStatus satisfactionStatus;

    // 공개 상태
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private PublicStatus publicStatus;

    // 상품 타입 구분
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private ProductType productType;

    // 상품 ID (FK)

    // 직접 추가 상품 ID (FK)

    // 회원 ID (FK)
}
