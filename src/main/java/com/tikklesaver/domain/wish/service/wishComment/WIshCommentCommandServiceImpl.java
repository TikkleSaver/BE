package com.tikklesaver.domain.wish.service.wishComment;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.wish.converter.WishCommentConverter;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.WishComment;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.domain.wish.repository.wishComment.WishCommentRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.WishHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WIshCommentCommandServiceImpl implements WishCommentCommandService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;
    private final WishCommentRepository wishCommentRepository;

    // 위시 댓글 생성
    @Override
    public WishComment createWishComment(Long memberId, Long wishId, WishCommentRequestDTO.CreateWishCommentDTO request){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));


        WishComment newWishComment = WishCommentConverter.toWishComment(member, wish, request);
        return wishCommentRepository.save(newWishComment);
    }

}
