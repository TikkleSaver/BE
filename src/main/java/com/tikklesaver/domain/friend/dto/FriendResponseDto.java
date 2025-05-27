package com.tikklesaver.domain.friend.dto;

import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FriendResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendProfileDTO {
        Long id; //친구 테이블 id
        Long myId;
        Long userId; //친구 id 가 있으면 친구인거고.
        String nickname;
        String profileUrl;
        FriendReqDTO friendReqInfo;
        int wishListNum;
        int challengeNum;
        int friendNum;
        List<ChallengeResponseDTO.ChallengePreViewDTO> challengeScrapedList;  // 타입 변경
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendReqDTO {
        Long requestId;
        Long senderId;
        Long receiverId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendDTO {
        Long id; //친구 테이블 id
        Long friendId;
        String nickname;
        String profileUrl;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendDTOList {
        Long memberId;
        List<FriendDTO> friendList;
    }
}
