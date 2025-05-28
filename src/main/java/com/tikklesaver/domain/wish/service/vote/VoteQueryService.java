package com.tikklesaver.domain.wish.service.vote;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.Vote;

public interface VoteQueryService {

    //  찬성/반대 나의 투표 여부 조회
    Vote getVote(Member member, Long wishId);
}
