package com.tikklesaver.domain.Challenge.dto.joinChallenge;

import com.tikklesaver.domain.Challenge.entity.enums.Status;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Builder
@Getter
public class JoinChallengeResponseDTO {

    private Long id;
    Long challengeId;
    Long memberId;
    Status status;
    LocalDateTime createdAt;

}
