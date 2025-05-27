package com.tikklesaver.domain.Challenge.repository.challenge;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.data.domain.Page;

public interface ChallengeRepositoryCustom {

    Page<Challenge> dynamicQueryBuilder(String keyword, Category category, Integer page);
}
