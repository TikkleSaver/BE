package com.tikklesaver.domain.Challenge.converter;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.dto.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChallengeConverter {

    public static Challenge toChallenge(Member member, ChallengeRequestDTO.CreateChallengeDTO request, Category category, String imageUrl) {
        return Challenge.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .challengeUrl(imageUrl)
                .missionMethods(request.getMissionMethods())
                .category(category)
                .publicStatus(request.getPublicStatus())
                .member(member)
                .build();
    }

    public static ChallengeResponseDTO.challengeResultDTO toChallengeResultDTO(Challenge challenge) {
        return ChallengeResponseDTO.challengeResultDTO.builder()
                .challengeId(challenge.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
