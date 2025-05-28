package com.tikklesaver.domain.Challenge.service.JoinChallenge;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.PublicStatus;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import com.tikklesaver.domain.Challenge.repository.challenge.ChallengeRepository;
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
public class JoinChallengeCommandServiceImpl implements JoinChallengeCommandService {

    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final JoinChallengeRepository joinChallengeRepository;

    @Override
    public JoinChallenge joinChallenge(Long memberId, Long challengeId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 챌린지를 찾을 수 없습니다. ID: " + challengeId));

        if(!memberId.equals(challenge.getMember().getId())) {
            JoinChallenge newJoinChallenge;
            if (challenge.getPublicStatus().equals(PublicStatus.PUBLIC)) {
               newJoinChallenge = ChallengeConverter.toJoinPublicChallenge(member, challenge);

            } else {
               newJoinChallenge = ChallengeConverter.toJoinPrivateChallenge(member, challenge);

            }
            return joinChallengeRepository.save(newJoinChallenge);
        }
        return null;
    }

    @Override
    public JoinChallenge acceptChallenge(Long memberId, Long joinChallengeId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        JoinChallenge joinChallenge = joinChallengeRepository.findById(joinChallengeId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 챌린지 참여를 찾을 수 없습니다. ID: " + joinChallengeId));
        if(!memberId.equals(joinChallenge.getChallenge().getMember().getId())) {
            throw new ChallengeHandler(ErrorStatus.CHALLENGE_NOT_AUTHOR);
        }

        joinChallenge.setStatus(Status.JOINED);
        return joinChallengeRepository.save(joinChallenge);

    }

}
