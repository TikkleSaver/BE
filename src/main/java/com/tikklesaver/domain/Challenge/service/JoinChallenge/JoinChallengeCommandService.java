package com.tikklesaver.domain.Challenge.service.JoinChallenge;


import com.tikklesaver.domain.Challenge.entity.JoinChallenge;

public interface JoinChallengeCommandService {

    JoinChallenge joinChallenge(Long memberId, Long challengerId);

}
