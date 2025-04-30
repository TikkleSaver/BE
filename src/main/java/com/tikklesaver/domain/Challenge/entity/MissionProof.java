package com.tikklesaver.domain.Challenge.entity;

import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class MissionProof extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 인증 사진
    @Column(nullable = false)
    private String imageUrl;

    // 내용
    @Column(nullable = false)
    private String content;

    // 회원 ID (FK)

    // 챌린지 ID (FK)

}
