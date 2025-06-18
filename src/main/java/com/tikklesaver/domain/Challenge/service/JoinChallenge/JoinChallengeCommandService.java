package com.tikklesaver.domain.Challenge.service.JoinChallenge;


import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;

import java.util.List;

public interface JoinChallengeCommandService {

    JoinChallenge joinChallenge(Long memberId, Long challengerId);

    JoinChallenge acceptChallenge(Long memberId, Long joinChallengeId);

    void rejectChallenge(Long memberId, Long joinChallengeId);
    void exitChallenge(Long memberId, Long challengeId);

    List<ChallengeDTO> getMyChallenges(Long memberId);
}
