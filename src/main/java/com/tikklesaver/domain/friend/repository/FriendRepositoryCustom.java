package com.tikklesaver.domain.friend.repository;

import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;

import java.util.List;

public interface FriendRepositoryCustom {
    // 나의 위시리스트 목록 구매 예정 조회
    List<FriendResponseDto.FriendDTO> findAllByMemberId(Long memberId);

    Long findFriendIdByMembers(Long memberId, Long userId);
}
