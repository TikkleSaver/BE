package com.tikklesaver.domain.member.converter;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
import com.tikklesaver.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static List<MemberResponseDto.MemberInfoDTO> toMemberInfoListDTO(List<Member> members) {
        return members.stream()
                .map(MemberConverter::toMemberInfoDTO)
                .collect(Collectors.toList());
    }

    public static MemberResponseDto.MemberInfoDTO toMemberInfoDTO(Member member) {
        return MemberResponseDto.MemberInfoDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();
    }

    public static MemberResponseDto.MemberProfileDTO toMemberProfileDTO(Member member, int wishListNum,
                                                                        int challengeNum,
                                                                        int friendNum, List<Challenge> challengeScrapedList) {

        // Challenge 리스트를 ChallengePreViewDTO 리스트로 변환 (ChallengeConverter 의 메서드 재활용)
        List<ChallengeResponseDTO.ChallengePreViewDTO> challengePreViewDTOs = challengeScrapedList.stream()
                .map(ChallengeConverter::challengePreViewDTO)
                .collect(Collectors.toList());

        return MemberResponseDto.MemberProfileDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .wishListNum(wishListNum)
                .challengeNum(challengeNum)
                .friendNum(friendNum)
                .challengeScrapedList(challengePreViewDTOs)
                .build();
    }

    // 지출 목표 금액 수정(지출 달력 페이지)
    public static MemberResponseDto.MemberGoalCostDTO toUpdateMemberGoalCostDTO(Member member) {
        return MemberResponseDto.MemberGoalCostDTO.builder()
                .memberId(member.getId())
                .goalCost(member.getGoalCost())
                .build();
    }
    
    // 지출 목표 금액 조회(지출 달력 페이지)
    public static MemberResponseDto.GetMemberGoalCostDTO toGetMemberGoalCostDTO(Long viewerId, Long memberId, Long goalCost) {
        return MemberResponseDto.GetMemberGoalCostDTO.builder()
                .viewerId(viewerId)
                .memberId(memberId)
                .goalCost(goalCost)
                .build();
    }
}
