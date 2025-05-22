package com.tikklesaver.domain.Challenge.service.Challenge;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import com.tikklesaver.domain.Challenge.repository.ChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.ChallengeScrapRepository;
import com.tikklesaver.domain.Challenge.repository.JoinChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeQueryServiceImpl implements ChallengeQueryService {

    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;
    private final JoinChallengeRepository joinChallengeRepository;
    private final ChallengeScrapRepository challengeScrapRepository;


    @Override
    public Page<Challenge> getAllChallenges(Long memberId, Long categoryId, Integer page) {

        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            return challengeRepository.findAllByCategory(category, pageRequest);
        }
        else {
            return challengeRepository.findAll(pageRequest);
        }
    }

    @Override
    public ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO getChallengePreview(Long memberId, Long challengeId) {

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 챌린지를 찾을 수 없습니다. ID: " + challengeId));
        Status status = joinChallengeRepository.getJoinChallengeStatusByChallengeIdAndMemberId(challengeId, memberId)
                .orElse(Status.NOT_APPLIED);
        boolean isScrapped = challengeScrapRepository.existsByChallengeIdAndMemberId(challengeId, memberId);
        Integer challengerCount = joinChallengeRepository.countJoinChallengeByChallengeIdAndStatus(challengeId, Status.JOINED);

        List<String> challengerImages = joinChallengeRepository.findTop3ChallengerImages(challengeId);
        return ChallengeConverter.challengePreviewWithStatusResponseDTO(challenge, status, isScrapped, challengerCount, challengerImages);

    }


}






