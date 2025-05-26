package com.tikklesaver.domain.wish.service.wishComment;

import com.tikklesaver.domain.wish.dto.wishComment.WishCommentRequestDTO;
import com.tikklesaver.domain.wish.entity.WishComment;

public interface WishCommentCommandService {

    // 위시 댓글 생성
    WishComment createWishComment(Long memberId, Long wishId, WishCommentRequestDTO.CreateWishCommentDTO request);
}
