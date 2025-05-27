package com.tikklesaver.domain.wish.service.wish;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.converter.WishConverter;
import com.tikklesaver.domain.wish.dto.wish.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.PublicStatus;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
import com.tikklesaver.domain.wish.entity.enums.SatisfactionStatus;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.WishHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishCommandServiceImpl implements WishCommandService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;

    // 존재하는 상품 위시에 추가 
    @Override
    public Wish createWishFromExistingProduct(Member member, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request){

        Wish newWish = WishConverter.toWish(member, product, request);
        return wishRepository.save(newWish);
    }

    // 존재하지 않는 상품 위시에 추가 (직접 추가한 상품)
    @Override
    public Wish createWishFromMyProduct(Member member, Product product, WishRequestDTO.CreateWishFromMyProductDTO request){

        Wish newWish = WishConverter.toWishMyProduct(member, product, request);
        return wishRepository.save(newWish);
    }

    // 존재하는 상품 위시 수정
    @Override
    public Wish updateWishFromExistingProduct(Member member, Long wishId, WishRequestDTO.UpdateWishFromExistingProductDTO request){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        wish.setPublicStatus(request.getPublicStatus());
        if (request.getSatisfactionStatus() != null)
            wish.setSatisfactionStatus(request.getSatisfactionStatus());

        return wishRepository.save(wish);
    }


    // 존재하지 않는 상품 위시 수정 (직접 추가한 상품)
    @Override
    public Wish updateWishFromMyProduct(Member member, Long wishId, WishRequestDTO.UpdateWishFromMyProductDTO request){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        wish.setPublicStatus(request.getPublicStatus());
        if (request.getSatisfactionStatus() != null)
            wish.setSatisfactionStatus(request.getSatisfactionStatus());

        return wishRepository.save(wish);
    }


    // 상품 위시 삭제
    @Override
    public Wish deleteWish(Member member, Long wishId){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        wishRepository.delete(wish);

        return wish;
    }

    // 나의 위시 공개/비공개 설정
    @Override
    public Wish updateWishPublicStatus(Member member, Long wishId){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        if (wish.getPublicStatus() == PublicStatus.PUBLIC){
            wish.setPublicStatus(PublicStatus.PRIVATE);
        } else {
            wish.setPublicStatus(PublicStatus.PUBLIC);
        }

        return wishRepository.save(wish);
    }

    // 나의 위시 구매 설정
    @Override
    public Wish updateWishPurchaseStatus(Member member, Long wishId) {

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        // 이미 구매 상태인 위시 예외처리
        if (wish.getPurchaseStatus() == PurchaseStatus.PURCHASE)
            throw new WishHandler(ErrorStatus.WISH_ALREADY_PURCHASED);

        wish.setPurchaseStatus(PurchaseStatus.PURCHASE);

        return wishRepository.save(wish);
    }


    // 나의 위시 만족/불만족 설정
    @Override
    public Wish updateWishSatisfactionStatus(Member member, Long wishId, SatisfactionStatus status){

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        // 아직 구매 상태가 아닌 위시 예외처리
        if (wish.getPurchaseStatus() == PurchaseStatus.PLANNED)
            throw new WishHandler(ErrorStatus.WISH_NOT_PURCHASED);

        wish.setSatisfactionStatus(status);

        return wishRepository.save(wish);
    }

}
