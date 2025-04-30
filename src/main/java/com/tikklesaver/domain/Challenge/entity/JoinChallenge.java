package com.tikklesaver.domain.Challenge.entity;

import com.tikklesaver.domain.Challenge.enums.Status;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class JoinChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가입일
    @Column(nullable = false)
    private Date createdAt;

    // 상태
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private Status status;

    // 회원 ID (FK)

    // 챌린지 ID (FK)

}
