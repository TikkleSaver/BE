package com.tikklesaver.domain.friendRequest.repository;

import com.tikklesaver.domain.friend.dto.FriendResponseDto;

import java.util.List;
import java.util.Optional;

public interface FriendReqRepositoryCustom {
    Optional<FriendResponseDto.FriendReqDTO> findByMembers(Long id, Long userId);
}
