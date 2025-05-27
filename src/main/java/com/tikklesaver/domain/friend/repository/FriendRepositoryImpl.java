package com.tikklesaver.domain.friend.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.entity.QFriend;
import com.tikklesaver.domain.member.entity.QMember;
import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.QVote;
import com.tikklesaver.domain.wish.entity.QWish;
import com.tikklesaver.domain.wish.entity.QWishComment;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
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

}
