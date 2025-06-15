package com.tikklesaver.domain.member.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;

    @Override
    public List<Member> searchByNicknameKeyword(String keyword, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            builder.and(member.nickname.containsIgnoreCase(keyword));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .offset(pageable.getOffset())       // 몇 번째부터 시작할지
                .limit(pageable.getPageSize())      // 몇 개 가져올지
                .fetch();
    }

}
