package com.tikklesaver.domain.wish.service.vote;

import com.tikklesaver.domain.wish.entity.Vote;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;

public interface VoteCommandService {
    
    // 위시 찬성/반대 투표
    Vote voteOnWish(Long memberId, Long wishId, LikeStatus status);
}
