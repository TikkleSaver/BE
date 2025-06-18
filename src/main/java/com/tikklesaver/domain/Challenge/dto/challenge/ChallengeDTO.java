package com.tikklesaver.domain.Challenge.dto.challenge;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChallengeDTO {

    Long challengeId;
    String title;
    String description;
    Long categoryId;
    String imgUrl;


}