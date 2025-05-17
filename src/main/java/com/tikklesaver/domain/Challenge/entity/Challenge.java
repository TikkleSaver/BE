package com.tikklesaver.domain.Challenge.entity;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.entity.enums.PublicStatus;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "challenge_mission_methods", joinColumns = @JoinColumn(name = "challenge_id"))
    @Column(name = "mission_method")
    private List<String> missionMethods = new ArrayList<>();
    
    // 챌린지 이미지
    @Column(nullable = false)
    private String challengeUrl;

    // 챌린지장 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Member member;

    // 카테고리 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
