package com.tikklesaver.domain.Challenge.service.Challenge;

import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;

public interface ChallengeCommandService {

    Challenge createChallenge(Long memberId, ChallengeRequestDTO.CreateChallengeDTO request);
}
