package com.tikklesaver.domain.wish.repository.wish;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;

import java.util.List;

public interface WishRepositoryCustom {

    // 위시리스트 상세 조회
    WishResponseDTO.WishDetailDTO getWishDetail(Long wishId);

    // 나의 위시리스트 목록 구매 예정 조회
    List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Member member);

    // 나의 위시리스트 목록 구매 완료 조회
    List<WishResponseDTO.MyWishPurchasedPreviewDTO> getMyWishPurchasedList(Member member);
}
