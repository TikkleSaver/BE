package com.tikklesaver.domain.friendRequest.dto;

import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.friendRequest.entity.enums.RequestStatus;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
import com.tikklesaver.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class FriendReqResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendReqDTO {
        Long requestId;
        MemberResponseDto.MemberInfoDTO sender;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendReqDTOList {
        Long receiverId;
        List<FriendReqDTO> friendReqList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendReqResDTO {
        Long requestId;
    }
}
