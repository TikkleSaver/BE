package com.tikklesaver.domain.wish.converter;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentRequestDTO;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.WishComment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WishCommentConverter {

    // 위시 댓글 생성
    public static WishComment toWishComment(Member member, Wish wish, WishCommentRequestDTO.CreateWishCommentDTO request){
        return WishComment.builder()
                .contents(request.getContents())
                .member(member)
                .wish(wish)
                .build();
    }

    // 위시 댓글 결과
    public static WishCommentResponseDTO.WishCommentResultDTO toWishCommentResultDTO(WishComment wishComment){
        return WishCommentResponseDTO.WishCommentResultDTO.builder()
                .wishCommentId(wishComment.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }


    // 위시 댓글 수정 결과
    public static WishCommentResponseDTO.UpdateWishCommentResultDTO toUpdateWishCommentResultDTO(WishComment wishComment){
        return WishCommentResponseDTO.UpdateWishCommentResultDTO.builder()
                .wishCommentId(wishComment.getId())
                .updatedAt(wishComment.getUpdatedAt())
                .build();
    }
}
