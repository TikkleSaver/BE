package com.tikklesaver.domain.friendRequest.service;

import com.tikklesaver.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public interface FriendRequestService {

    void addFriendRequest(Member member, @NotNull Long receiverId);

    void addFriend(Member member, Long id);

    void deleteFriendRequest(Member member, Long id);
}
