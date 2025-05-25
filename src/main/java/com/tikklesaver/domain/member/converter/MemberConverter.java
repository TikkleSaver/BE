package com.tikklesaver.domain.member.converter;

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


}
