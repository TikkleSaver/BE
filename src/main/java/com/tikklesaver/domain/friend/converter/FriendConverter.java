package com.tikklesaver.domain.friend.converter;

import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friendRequest.dto.FriendReqResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

import static com.tikklesaver.domain.member.converter.MemberConverter.toMemberInfoDTO;

public class FriendConverter {

    // Frienddto 리스트 → FriendReqDTOList 로 변환
    public static FriendResponseDto.FriendDTOList tofriendDTOList(Member member, List<FriendResponseDto.FriendDTO> friends) {
        return FriendResponseDto.FriendDTOList.builder()
                .memberId(member.getId())
                .friendList(friends)
                .build();
    }

}
