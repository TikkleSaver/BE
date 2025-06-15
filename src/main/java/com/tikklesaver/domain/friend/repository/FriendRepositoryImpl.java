package com.tikklesaver.domain.friend.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.entity.QFriend;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QFriend friend = QFriend.friend;
    private final QMember member = QMember.member;

    @Override
    public Long countByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(friend.count())
                .from(friend)
                .where(
                        friend.member1.id.eq(memberId)
                                .or(friend.member2.id.eq(memberId))
                )
                .fetchOne(); // 결과는 Long 하나
    }


    @Override
    public List<FriendResponseDto.FriendDTO> findAllByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        FriendResponseDto.FriendDTO.class,
                        friend.id,
                        member.id,
                        member.nickname,
                        member.profileUrl
                ))
                .from(friend)
                .join(member).on(
                        friend.member1.id.eq(memberId).and(member.id.eq(friend.member2.id))
                                .or(friend.member2.id.eq(memberId).and(member.id.eq(friend.member1.id)))
                )
                .fetch();
    }

    @Override
    public Long findFriendIdByMembers(Long memberId, Long userId) {
        Long smaller = Math.min(memberId, userId);
        Long bigger = Math.max(memberId, userId);

        Long result = jpaQueryFactory
                .select(friend.id)
                .from(friend)
                .where(
                        friend.member1.id.eq(smaller)
                                .and(friend.member2.id.eq(bigger))
                )
                .fetchOne();

        return result != null ? result : 0L;
    }


}
