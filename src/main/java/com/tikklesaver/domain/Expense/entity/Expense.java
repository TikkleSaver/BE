package com.tikklesaver.domain.Expense.entity;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Expense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지출명
    @Column(nullable = false)
    private String expenseName;

    // 지출처
    @Column(nullable = false)
    private String expensePlace;

    // 지출금액
    @Column(nullable = false)
    private Long cost;

    // 지출 날짜
    @Column(nullable = false)
    private Date expenseDate;

    // 썸네일
    private String image;

    // 카테고리 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 지출한 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
