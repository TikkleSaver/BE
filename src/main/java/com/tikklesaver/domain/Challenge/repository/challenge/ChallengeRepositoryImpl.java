package com.tikklesaver.domain.Challenge.repository.challenge;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.QChallenge;
import com.tikklesaver.domain.Challenge.entity.QJoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QChallenge challenge = QChallenge.challenge;
    private final QueryFactory queryFactory;

    @Override
    public Page<Challenge> dynamicQueryBuilder(String keyword, Category category, Integer page) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            predicate.and(
                    challenge.title.containsIgnoreCase(keyword)
            );
        }

        if (category != null) {
            predicate.and(challenge.category.eq(category));
        }

        int pageSize = 12;
        int pageIndex = (page != null && page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<Challenge> results = jpaQueryFactory
                .selectFrom(challenge)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(challenge)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

}
