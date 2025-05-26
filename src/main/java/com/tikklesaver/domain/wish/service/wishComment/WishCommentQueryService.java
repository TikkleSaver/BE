package com.tikklesaver.domain.wish.service.wishComment;

import com.tikklesaver.domain.wish.dto.wishComment.WishCommentResponseDTO;

import java.util.List;

public interface WishCommentQueryService {
    
    // 위시 댓글 목록 조회
    List<WishCommentResponseDTO.WishCommentPreviewDTO> getWishCommentList(Long memberId, Long wishId);
}
