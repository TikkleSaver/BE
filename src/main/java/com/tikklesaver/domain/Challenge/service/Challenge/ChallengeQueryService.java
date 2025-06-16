package com.tikklesaver.domain.Challenge.service.Challenge;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.data.domain.Page;

public interface ChallengeQueryService {

    Page<Challenge> getAllChallenges( Long categoryId, Integer page);
    ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO getChallengePreview(Long memberId, Long challengeId);

    Page<Challenge> searchChallenges(String keyword, Long categoryId, Integer page);
}
