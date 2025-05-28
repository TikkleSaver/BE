package com.tikklesaver.domain.wish.converter;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.dto.wish.WishRequestDTO;
import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WishConverter {

    // 위시 생성
    public static Wish toWish(Member member, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request){
        return Wish.builder()
                .purchaseStatus(PurchaseStatus.PLANNED)
                .publicStatus(request.getPublicStatus())
                .member(member)
                .product(product)
                .build();
    }

    // 위시 생성 (존재하지 않는 상품)
    public static Wish toWishMyProduct(Member member, Product product, WishRequestDTO.CreateWishFromMyProductDTO request){
        return Wish.builder()
                .purchaseStatus(PurchaseStatus.PLANNED)
                .publicStatus(request.getPublicStatus())
                .member(member)
                .product(product)
                .build();
    }

    // 위시 결과
    public static WishResponseDTO.WishResultDTO toWishResultDTO(Wish wish){
        return WishResponseDTO.WishResultDTO.builder()
                .wishId(wish.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 위시 수정 결과
    public static WishResponseDTO.UpdateWishResultDTO toWishUpdateResultDTO(Wish wish){
        return WishResponseDTO.UpdateWishResultDTO.builder()
                .wishId(wish.getId())
                .updatedAt(wish.getUpdatedAt())
                .build();
    }

    // 나의 위시리스트 목록 구매 예정 조회
    public static WishResponseDTO.MyWishPlannedPreviewListDTO myWishPlannedPreviewListDTO(List<WishResponseDTO.MyWishPlannedPreviewDTO> myWishPlannedList){
        return WishResponseDTO.MyWishPlannedPreviewListDTO.builder()
                .myWishPlannedLst(myWishPlannedList)
                .build();
    }

    // 나의 위시리스트 목록 구매 예정 조회
    public static WishResponseDTO.MyWishPurchasedPreviewListDTO myWishPurchasedPreviewListDTO(List<WishResponseDTO.MyWishPurchasedPreviewDTO> myWishPurchasedList){
        return WishResponseDTO.MyWishPurchasedPreviewListDTO.builder()
                .myWishPurchasedLst(myWishPurchasedList)
                .build();
    }

    // 친구의 위시리스트 목록 구매 예정 조회
    public static WishResponseDTO.FriendWishPlannedPreviewListDTO friendWishPlannedPreviewListDTO(List<WishResponseDTO.FriendWishPlannedPreviewDTO> friendWishPlannedList){
        return WishResponseDTO.FriendWishPlannedPreviewListDTO.builder()
                .friendWishPlannedList(friendWishPlannedList)
                .build();
    }

    // 친구의 위시리스트 목록 구매 예정 조회
    public static WishResponseDTO.FriendWishPurchasedPreviewListDTO friendWishPurchasedPreviewListDTO(List<WishResponseDTO.FriendWishPurchasedPreviewDTO> friendWishPurchasedList){
        return WishResponseDTO.FriendWishPurchasedPreviewListDTO.builder()
                .friendWishPurchasedList(friendWishPurchasedList)
                .build();
    }
}
