package com.tikklesaver.domain.wish.service;

import com.tikklesaver.domain.wish.dto.WishResponseDTO;
import com.tikklesaver.domain.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishQueryServiceImpl implements WishQueryService {

    private final WishRepository wishRepository;

    // 나의 위시리스트 목록 구매 예정 조회
    @Override
    public List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Long memberId){

        return wishRepository.getMyWishPlannedList(memberId);
    }

}
