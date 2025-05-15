package com.tikklesaver.domain.Challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ChallengeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class challengeResultDTO {
        Long challengeId;
        LocalDateTime createdAt;
    }
}
