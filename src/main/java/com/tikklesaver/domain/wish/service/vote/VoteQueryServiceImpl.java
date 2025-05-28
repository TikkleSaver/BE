package com.tikklesaver.domain.wish.service.vote;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.Vote;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.repository.vote.VoteRepository;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.VoteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteQueryServiceImpl implements VoteQueryService {

    private final VoteRepository voteRepository;
    private final WishRepository wishRepository;

    //  찬성/반대 나의 투표 여부 조회
    @Override
    public Vote getVote(Member member, Long wishId){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new VoteHandler(ErrorStatus.WISH_NOT_FOUND));

        Vote vote = voteRepository.findByMemberAndWish(member, wish)
                .orElse(null);

        return vote;
    }

}
