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
    public List<Member> searchByNicknameKeyword(String keyword, Pageable pageable, Long currentMemberId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            builder.and(member.nickname.containsIgnoreCase(keyword));
        }

        if (currentMemberId != null) {
            builder.and(member.id.ne(currentMemberId)); // 현재 로그인한 사용자 제외
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
