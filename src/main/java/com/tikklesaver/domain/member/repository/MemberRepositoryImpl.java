package com.tikklesaver.domain.member.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;

    @Override
    public List<Member> searchByNicknameKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of(); // 빈 검색어면 빈 리스트 반환
        }

        return queryFactory
                .selectFrom(member)
                .where(member.nickname.containsIgnoreCase(keyword)) // 대소문자 무시 포함 검색
                .fetch();
    }
}
