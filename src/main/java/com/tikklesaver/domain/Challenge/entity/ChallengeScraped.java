package com.tikklesaver.domain.Challenge.entity;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class ChallengeScraped extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 챌린지 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    protected ChallengeScraped() {}

    public static ChallengeScraped of(Challenge challenge, Member member) {
        ChallengeScraped scraped = new ChallengeScraped();
        scraped.challenge = challenge;
        scraped.member = member;
        return scraped;
    }



}
