package com.tikklesaver.domain.wish.dto.wishComment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public class WishCommentRequestDTO {

    // 위시 댓글 생성
    @Getter
    public static class CreateWishCommentDTO {

        @NotEmpty
        String contents;
    }

    // 위시 댓글 수정
    @Getter
    public static class UpdateWishCommentDTO {

        @NotEmpty
        String contents;
    }
}
