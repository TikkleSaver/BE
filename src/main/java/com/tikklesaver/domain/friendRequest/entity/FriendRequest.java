package com.tikklesaver.domain.friendRequest.entity;

import com.tikklesaver.domain.friendRequest.entity.enums.RequestStatus;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 요청 상태 (PENDING, ACCEPTED, REJECTED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    // 친구 요청 보낸 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    // 친구 요청 받은 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;
}
