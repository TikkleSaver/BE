package com.tikklesaver.domain.friendRequest.repository;

import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendReqRepository extends JpaRepository<FriendRequest, Long>{

    boolean existsBySenderAndReceiver(Member sender, Member receiver);
    List<FriendRequest> findByReceiver(Member receiver);
}
