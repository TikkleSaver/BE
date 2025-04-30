package com.tikklesaver.domain.member.entity;

import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 아이디
    @Column(nullable = false)
    private String loginId;
    
    // 비밀번호
    @Column(nullable = false)
    private String password;
    
    // 닉네임
    @Column(nullable = false)
    private String nickname;
    
    // 하루 지출 목표 금액
    @Column(nullable = false)
    private String goalCost;
    
    // 프로플 이미지
    private String profileUrl;
    
    // 활동 상태
    private String status;
}
