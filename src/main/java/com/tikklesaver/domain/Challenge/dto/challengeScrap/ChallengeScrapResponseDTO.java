package com.tikklesaver.domain.Challenge.dto.challengeScrap;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChallengeScrapResponseDTO {

    private Long challengeId;
    private Long memberId;
    private boolean isScrapped;
    private String message;

}
