package com.tikklesaver.domain.friendRequest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.entity.QFriend;
import com.tikklesaver.domain.friend.repository.FriendRepositoryCustom;
import com.tikklesaver.domain.friendRequest.entity.QFriendRequest;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendReqRepositoryImpl implements FriendReqRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QFriend friend = QFriend.friend;
    private final QMember member = QMember.member;
    private final QFriendRequest friendRequest = QFriendRequest.friendRequest;


    @Override
    public Optional<FriendResponseDto.FriendReqDTO> findByMembers(Long member1Id, Long member2Id) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(Projections.constructor(
                                FriendResponseDto.FriendReqDTO.class,
                                friendRequest.id,
                                friendRequest.sender.id,
                                friendRequest.receiver.id
                        ))
                        .from(friendRequest)
                        .where(
                                friendRequest.sender.id.eq(member1Id).and(friendRequest.receiver.id.eq(member2Id))
                                        .or(friendRequest.sender.id.eq(member2Id).and(friendRequest.receiver.id.eq(member1Id)))
                        )
                        .fetchOne()
        );
    }
}
