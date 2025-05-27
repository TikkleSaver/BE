package com.tikklesaver.domain.friend.repository;

import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> , FriendRepositoryCustom {
    boolean existsByMember1AndMember2(Member member, Member member1);
}
