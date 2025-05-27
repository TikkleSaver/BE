package com.tikklesaver.domain.Challenge.service.ChallengeScrap;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challengeScrap.ChallengeScrapResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.ChallengeScraped;
import com.tikklesaver.domain.Challenge.repository.challenge.ChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.ChallengeScrapRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeScrapCommandServiceImpl implements ChallengeScrapCommandService {


    private final ChallengeScrapRepository challengeScrapRepository;
    private final ChallengeRepository challengeRepository;
    private final MemberRepository memberRepository;

    @Override
    public ChallengeScrapResponseDTO changeScrap(Long challengeId, Long memberId) {

        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        Optional<ChallengeScraped> existingScrap = challengeScrapRepository.findByChallengeIdAndMemberId(challengeId,memberId);
        boolean isScrapped;
        String message;

        if (existingScrap.isPresent()) {
            challengeScrapRepository.delete(existingScrap.get());
            isScrapped = false;
            message="스크랩이 삭제되었습니다";
        } else {
            ChallengeScraped scraped = ChallengeScraped.of(challenge, member);
            challengeScrapRepository.save(scraped);
            isScrapped = true;
            message="스크랩이 생성되었습니다";
        }

        return ChallengeConverter.challengeScrapResponseDTO(challenge, member, isScrapped, message);

    }
}
