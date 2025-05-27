package com.tikklesaver.domain.Challenge.repository.ChallengeRepository;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeRepositoryCustom {

    Page<Challenge> findAllByCategory(Category category, Pageable pageable);
}
