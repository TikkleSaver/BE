package com.tikklesaver.domain.wish.service.vote;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.wish.converter.VoteConverter;
import com.tikklesaver.domain.wish.entity.Vote;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.domain.wish.repository.vote.VoteRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.VoteHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteCommandServiceImpl implements VoteCommandService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;
    private final VoteRepository voteRepository;


    // 위시 찬성/반대 투표
    @Override
    public Vote voteOnWish(Long memberId, Long wishId, LikeStatus status){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new VoteHandler(ErrorStatus.WISH_NOT_FOUND));

        // 이미 투표 여부
        voteRepository.findByMemberAndWish(member, wish)
                .ifPresent(vote -> {
                    throw new VoteHandler(ErrorStatus.ALREADY_VOTED);
                });

        Vote newVote = VoteConverter.toVote(member, wish, status);

        return voteRepository.save(newVote);
    }
}
