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
import com.tikklesaver.global.apiPayload.exception.handler.WishCommentHandler;
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
    public WishComment createWishComment(Member member, Long wishId, WishCommentRequestDTO.CreateWishCommentDTO request){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishCommentHandler(ErrorStatus.WISH_NOT_FOUND));

        WishComment newWishComment = WishCommentConverter.toWishComment(member, wish, request);
        return wishCommentRepository.save(newWishComment);
    }


    // 위시 댓글 수정
    @Override
    public WishComment updateWishComment(Member member, Long commentId, WishCommentRequestDTO.UpdateWishCommentDTO request){

        WishComment wishComment = wishCommentRepository.findById(commentId)
                .orElseThrow(() -> new WishCommentHandler(ErrorStatus.WISH_COMMENT_NOT_FOUND));

        // 작성자 일치 유무
        if (wishComment.getMember() != member)
            throw new WishCommentHandler(ErrorStatus.WISH_COMMENT_NOT_AUTHOR);

        wishComment.setContents(request.getContents());

        return wishCommentRepository.save(wishComment);
    }


    // 위시 댓글 삭제
    @Override
    public void deleteWishComment(Member member, Long commentId){

        WishComment wishComment = wishCommentRepository.findById(commentId)
                .orElseThrow(() -> new WishCommentHandler(ErrorStatus.WISH_COMMENT_NOT_FOUND));

        // 작성자 일치 유무
        if (wishComment.getMember() != member)
            throw new WishCommentHandler(ErrorStatus.WISH_COMMENT_NOT_AUTHOR);

        wishCommentRepository.delete(wishComment);
    }

}
