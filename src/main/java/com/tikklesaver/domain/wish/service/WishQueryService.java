package com.tikklesaver.domain.wish.service;

import com.tikklesaver.domain.wish.dto.WishResponseDTO;

import java.util.List;

public interface WishQueryService {

    // 나의 위시리스트 목록 구매 예정 조회
    List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Long memberId);
}
