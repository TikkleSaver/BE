package com.tikklesaver.domain.friend.repository;

import com.tikklesaver.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
