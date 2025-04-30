package com.tikklesaver.domain.friend.entity;

import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상태
    @Column(nullable = false)
    private String status;

    // 신청 일시
    @Column(nullable = false)
    private Date requestedAt;

    // 응답 일시
    private Date respondedAt;

    // 신청한 회원 ID (FK)

    // 신청받은 회원 ID (FK)

}
