package com.tikklesaver.domain.Expense.entity;

import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class ExpenseComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지출한 사용자 ID (CK)
    @Id
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    // 내용
    @Column(nullable = false)
    private String comment;

    // 지출날짜
    @Column(nullable = false)
    private Date expenseDate;

    // 댓글 작성자 ID (FK)
}
