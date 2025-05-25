package com.tikklesaver.domain.wish.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class VoteResponseDTO {

    // 찬성/반대 투표 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteResultDTO {
        Long voteId;
        LocalDateTime createdAt;
    }
}
