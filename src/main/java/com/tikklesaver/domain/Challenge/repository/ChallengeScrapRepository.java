package com.tikklesaver.domain.Challenge.repository;

import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeScrapRepository extends JpaRepository<ChallengeScraped, Long> {
    boolean existsByChallengeIdAndMemberId(Long challengeId, Long memberId);
}
