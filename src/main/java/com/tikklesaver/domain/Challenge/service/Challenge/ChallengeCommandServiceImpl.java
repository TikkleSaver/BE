package com.tikklesaver.domain.Challenge.service.Challenge;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.repository.ChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.JoinChallengeRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.ChallengeHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeCommandServiceImpl implements ChallengeCommandService {

    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Challenge createChallenge(Long memberId, ChallengeRequestDTO.CreateChallengeDTO request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        String imageUrl = "String";

        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new ChallengeHandler(ErrorStatus.TITLE_NOT_PROVIDED);
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new ChallengeHandler(ErrorStatus.DESCRIPTION_NOT_PROVIDED);
        }

        Challenge newChallenge = ChallengeConverter.toChallenge(member, request,category,imageUrl);
        return challengeRepository.save(newChallenge);
    }
}
