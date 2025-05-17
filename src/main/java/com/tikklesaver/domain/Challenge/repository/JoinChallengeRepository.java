package com.tikklesaver.domain.Challenge.repository;

import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JoinChallengeRepository extends JpaRepository<JoinChallenge, Long> {

    @Query("SELECT jc.status FROM JoinChallenge jc WHERE jc.challenge.id = :challengeId AND jc.member.id = :memberId")
    Optional<Status> getJoinChallengeStatusByChallengeIdAndMemberId(Long challengeId,  Long memberId);
}
