package com.tikklesaver.domain.Challenge.service.ChallengeScrap;

import com.tikklesaver.domain.Challenge.dto.challengeScrap.ChallengeScrapResponseDTO;

public interface ChallengeScrapCommandService {

    ChallengeScrapResponseDTO changeScrap(Long challengeId, Long memberId);




}
