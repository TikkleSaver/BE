package com.tikklesaver.domain.friendRequest.service;

import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface FriendRequestService {

    void addFriendRequest(Member member, @NotNull Long receiverId);

    void addFriend(Member member, Long id);

    String deleteFriendRequest(Member member, Long id);

    List<FriendRequest> getFriendRequests(Member member);

    FriendResponseDto.FriendReqDTO getFriendRequest(Member member, Long userId);
}
