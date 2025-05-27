package com.tikklesaver.domain.Challenge.repository;

import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JoinChallengeRepository extends JpaRepository<JoinChallenge, Long> {

    @Query("SELECT jc.status FROM JoinChallenge jc WHERE jc.challenge.id = :challengeId AND jc.member.id = :memberId")
    Optional<Status> getJoinChallengeStatusByChallengeIdAndMemberId(Long challengeId,  Long memberId);

    Integer countJoinChallengeByChallengeId(Long challengeId);

    @Query(
            value = "SELECT m.profile_url " +
                    "FROM join_challenge jc " +
                    "JOIN member m ON jc.member_id = m.id " +
                    "WHERE jc.challenge_id = :challengeId " +
                    "ORDER BY jc.created_at ASC " +
                    "FETCH FIRST 3 ROWS ONLY",
            nativeQuery = true
    )
    List<String> findTop3ChallengerImages(Long challengeId);


    Integer countJoinChallengeByChallengeIdAndStatus(Long challengeId, Status status);

    int countByMemberId(Long memberId);
}
