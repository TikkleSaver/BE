package com.tikklesaver.domain.Challenge.repository;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
