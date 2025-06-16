package com.tikklesaver.domain.Challenge.service.JoinChallenge;


import com.tikklesaver.domain.Challenge.entity.JoinChallenge;

public interface JoinChallengeCommandService {

    JoinChallenge joinChallenge(Long memberId, Long challengerId);

    JoinChallenge acceptChallenge(Long memberId, Long joinChallengeId);

    void rejectChallenge(Long memberId, Long joinChallengeId);
    void exitChallenge(Long memberId, Long challengeId);
}
