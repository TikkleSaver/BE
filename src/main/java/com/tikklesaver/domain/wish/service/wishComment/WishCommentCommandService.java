package com.tikklesaver.domain.wish.service.wishComment;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentRequestDTO;
import com.tikklesaver.domain.wish.entity.WishComment;

public interface WishCommentCommandService {

    // 위시 댓글 생성
    WishComment createWishComment(Member member, Long wishId, WishCommentRequestDTO.CreateWishCommentDTO request);

    // 위시 댓글 수정
    WishComment updateWishComment(Member member, Long commentId, WishCommentRequestDTO.UpdateWishCommentDTO request);

    // 위시 댓글 삭제
    void deleteWishComment(Member member, Long commentId);
}
