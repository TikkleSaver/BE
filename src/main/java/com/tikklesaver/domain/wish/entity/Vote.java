package com.tikklesaver.domain.wish.entity;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Vote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 찬반 상태
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private LikeStatus likeStatus;
    
    // 위시 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id")
    private Wish wish;
    
    // 회원 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
