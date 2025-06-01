package com.tikklesaver.domain.friendRequest.converter;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.friendRequest.dto.FriendReqResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
import com.tikklesaver.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

import static com.tikklesaver.domain.member.converter.MemberConverter.toMemberInfoDTO;

public class FriendReqConverter {

    // FriendRequest 리스트 → FriendReqDTOList 로 변환
    public static FriendReqResponseDto.FriendReqDTOList toReqDTOList(Member receiver, List<FriendRequest> friendRequests) {
        List<FriendReqResponseDto.FriendReqDTO> dtoList = friendRequests.stream()
                .map(FriendReqConverter::toFriendReqDTO)
                .collect(Collectors.toList());

        return FriendReqResponseDto.FriendReqDTOList.builder()
                .receiverId(receiver.getId())
                .friendReqList(dtoList)
                .build();
    }

    // FriendRequest → FriendReqDTO 변환
    public static FriendReqResponseDto.FriendReqDTO toFriendReqDTO(FriendRequest request) {
        return FriendReqResponseDto.FriendReqDTO.builder()
                .requestId(request.getId())
                .sender(toMemberInfoDTO(request.getSender()))
                .build();
    }

    // FriendRequest → FriendReqDTO 변환
    public static FriendReqResponseDto.FriendReqResDTO toFriendResDTO(Long requestId) {
        return FriendReqResponseDto.FriendReqResDTO.builder()
                .requestId(requestId)
                .build();
    }
}
