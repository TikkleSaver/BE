package com.tikklesaver.domain.Challenge.service.Challenge;

import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeDTO;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChallengeQueryService {

    Page<Challenge> getAllChallenges( Long categoryId, Integer page);
    ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO getChallengePreview(Long memberId, Long challengeId);

    List<ChallengeDTO> getTop4Challenges();
    Page<Challenge> searchChallenges(String keyword, Long categoryId, Integer page);
}
