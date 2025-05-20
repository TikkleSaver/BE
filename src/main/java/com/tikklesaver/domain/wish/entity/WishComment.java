package com.tikklesaver.domain.wish.entity;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class WishComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내용
    @Column(nullable = false)
    private String contents;

    // 위시 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id")
    private Wish wish;

    // 회원 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
