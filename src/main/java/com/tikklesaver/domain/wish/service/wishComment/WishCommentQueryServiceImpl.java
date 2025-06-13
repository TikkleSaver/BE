package com.tikklesaver.domain.wish.service.wishComment;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.converter.WishCommentConverter;
import com.tikklesaver.domain.wish.dto.wishComment.WishCommentResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.WishComment;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.domain.wish.repository.wishComment.WishCommentRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.WishCommentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishCommentQueryServiceImpl implements WishCommentQueryService {

    private final WishRepository wishRepository;
    private final WishCommentRepository wishCommentRepository;

    // 위시 댓글 목록 조회
    @Override
    public List<WishCommentResponseDTO.WishCommentPreviewDTO> getWishCommentList(Long wishId, Member member){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishCommentHandler(ErrorStatus.WISH_NOT_FOUND));

        List<WishComment> wishCommentList = wishCommentRepository.findAllByWish(wish);



        return wishCommentList.stream()
                .map(comment -> WishCommentConverter.toGetWishCommentResultDTO(comment, member))
                .toList();

    }
}
