package com.tikklesaver.domain.friend.service;

import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface FriendService {

    List<FriendResponseDto.FriendDTO> getFriends(Member member);

    void deleteFriend(Member member, Long id);

    Long getFriendId(Member member, Long userId);
}
