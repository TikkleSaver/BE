package com.tikklesaver.domain.wish.converter;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.dto.vote.VoteResponseDTO;
import com.tikklesaver.domain.wish.entity.Vote;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoteConverter {

    // 찬반 투표 생성
    public static Vote toVote(Member member, Wish wish, LikeStatus likeStatus){
        return Vote.builder()
                .likeStatus(likeStatus)
                .member(member)
                .wish(wish)
                .build();
    }

    // 찬반 투표 생성 결과
    public static VoteResponseDTO.VoteResultDTO toVoteResultDTO(Vote vote){
        return VoteResponseDTO.VoteResultDTO.builder()
                .voteId(vote.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
