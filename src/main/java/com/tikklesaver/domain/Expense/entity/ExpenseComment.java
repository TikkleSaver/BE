package com.tikklesaver.domain.Expense.entity;

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
public class ExpenseComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지출한 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 내용
    @Column(nullable = false)
    private String content;

    // 지출날짜
    @Column(nullable = false)
    private Date expenseDate;

    // 댓글 작성자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private Member commenter;
}
