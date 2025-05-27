package com.tikklesaver.domain.Challenge.repository;

import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeScrapRepository extends JpaRepository<ChallengeScraped, Long> {
    boolean existsByChallengeIdAndMemberId(Long challengeId, Long memberId);
    Optional<ChallengeScraped> findByChallengeIdAndMemberId(Long challengeId, Long memberId);
    List<ChallengeScraped> findAllByMemberId(Long memberId);
}
