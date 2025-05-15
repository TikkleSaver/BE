package com.tikklesaver.domain.Challenge.service;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.dto.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ChallengeQueryService {
    Challenge createChallenge(Long memberId, ChallengeRequestDTO.CreateChallengeDTO request);
    Page<Challenge> getAllChallenges(Long memberId, Long categoryId, Integer page);

}
