package com.tikklesaver.domain.Challenge.entity;

import com.tikklesaver.domain.Challenge.entity.enums.PublicStatus;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Challenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 챌린지명
    @Column(nullable = false)
    private String title;

    // 소개
    @Column(nullable = false)
    private String description;
    
    // 공개 여부
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private PublicStatus publicStatus;
    
    // 인증 방식

    
    // 챌린지 이미지
    @Column(nullable = false)
    private String challengeUrl;

    // 챌린지장 ID (FK)

    // 카테고리 ID (FK)

}
