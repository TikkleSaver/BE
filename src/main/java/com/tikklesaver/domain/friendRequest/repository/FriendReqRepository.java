package com.tikklesaver.domain.friendRequest.repository;

import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendReqRepository extends JpaRepository<FriendRequest, Long>, FriendReqRepositoryCustom {

    boolean existsBySenderAndReceiver(Member sender, Member receiver);
}
