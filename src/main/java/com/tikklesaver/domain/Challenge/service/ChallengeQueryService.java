package com.tikklesaver.domain.Challenge.service;

import com.tikklesaver.domain.Challenge.dto.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.web.multipart.MultipartFile;

public interface ChallengeQueryService {
    Challenge createChallenge(Long memberId, ChallengeRequestDTO.CreateChallengeDTO request);
}
