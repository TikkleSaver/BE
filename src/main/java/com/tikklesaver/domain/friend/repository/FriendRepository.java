package com.tikklesaver.domain.friend.repository;

import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> , FriendRepositoryCustom {
    boolean existsByMember1AndMember2(Member member, Member member1);

    // 친구 찾기1
    List<Friend> findAllByMember1(Member member1);

    // 친구 찾기2
    List<Friend> findAllByMember2(Member member2);
}
