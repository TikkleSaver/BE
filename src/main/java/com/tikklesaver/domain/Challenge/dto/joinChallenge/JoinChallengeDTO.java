package com.tikklesaver.domain.Challenge.dto.joinChallenge;

import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class JoinChallengeDTO {

    Long challengeId;
    String title;
    Set<JoinChallenge> joinChallenges;

}
