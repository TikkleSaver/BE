package com.tikklesaver.domain.friend.converter;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friendRequest.dto.FriendReqResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.member.converter.MemberConverter;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
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

    public static FriendResponseDto.FriendProfileDTO toFriendProfileDTO
            (Member member, int wishListNum,
             int challengeNum,
             int friendNum, List<Challenge> challengeScrapedList,
             Long friendId, FriendResponseDto.FriendReqDTO friendReqInfo) {

        // Challenge 리스트를 ChallengePreViewDTO 리스트로 변환 (ChallengeConverter 의 메서드 재활용)
        List<ChallengeResponseDTO.ChallengePreViewDTO> challengePreViewDTOs = challengeScrapedList.stream()
                .map(ChallengeConverter::challengePreViewDTO)
                .collect(Collectors.toList());

        return FriendResponseDto.FriendProfileDTO.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .friendId(friendId)
                .friendReqInfo(friendReqInfo)
                .wishListNum(wishListNum)
                .challengeNum(challengeNum)
                .friendNum(friendNum)
                .challengeScrapedList(challengePreViewDTOs)
                .build();
    }

}
