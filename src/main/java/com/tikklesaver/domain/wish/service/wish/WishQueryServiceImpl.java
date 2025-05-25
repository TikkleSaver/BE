package com.tikklesaver.domain.wish.service.wish;

import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishQueryServiceImpl implements WishQueryService {

    private final WishRepository wishRepository;

    // 위시리스트 상세 조회
    @Override
    public WishResponseDTO.WishDetailDTO getWishDetail(Long wishId){
        return wishRepository.getWishDetail(wishId);
    }

    // 나의 위시리스트 목록 구매 예정 조회
    @Override
    public List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Long memberId){

        return wishRepository.getMyWishPlannedList(memberId);
    }

    // 나의 위시리스트 목록 구매 완료 조회
    @Override
    public List<WishResponseDTO.MyWishPurchasedPreviewDTO> getMyWishPurchasedList(Long memberId){

        return wishRepository.getMyWishPurchasedList(memberId);
    }

}
