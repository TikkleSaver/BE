package com.tikklesaver.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDto {
//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class MemberListDTO {
//        List<MemberInfoDTO> memberList;
//    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfoDTO {
        Long id;
        String nickname;
        String profileUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberProfileDTO {
        Long id;
        String nickname;
        String profileUrl;
        int wishListNum;
        int challengeNum;
        int friendNum;
        List<ChallengeResponseDTO.ChallengePreViewDTO> challengeScrapedList;  // 타입 변경
    }


    // 특정 사용자의 지출 목표 금액 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberGoalCostDTO {
        Long memberId;
        Long goalCost;
    }

    // 특정 사용자의 지출 목표 금액 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMemberGoalCostDTO {
        Long viewerId;
        Long memberId;
        Long goalCost;
    }
}
