package com.tikklesaver.domain.Challenge.repository.challenge;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeRepositoryCustom {

    Page<Challenge> findAllByCategory(Category category, Pageable pageable);

    @Query("""
    SELECT jc.challenge
    FROM JoinChallenge jc
    WHERE jc.status = 'JOINED'
    GROUP BY jc.challenge
    ORDER BY COUNT(jc.id) DESC
    """)
    List<Challenge> findTop4ByMostJoined(Pageable pageable);

}
