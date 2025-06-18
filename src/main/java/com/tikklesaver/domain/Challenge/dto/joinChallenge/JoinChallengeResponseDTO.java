package com.tikklesaver.domain.Challenge.dto.joinChallenge;

import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class JoinChallengeResponseDTO {

    private Long id;
    Long challengeId;
    Long memberId;
    Status status;
    LocalDateTime createdAt;

}

