package com.tikklesaver.domain.wish.service;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.converter.WishConverter;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.enums.PublicStatus;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
import com.tikklesaver.domain.wish.repository.WishRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.WishHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class WishCommandServiceImpl implements WishCommandService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;

    // 존재하는 상품 위시에 추가 
    @Override
    public Wish createWishFromExistingProduct(Long memberId, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish newWish = WishConverter.toWish(member, product, request);
        return wishRepository.save(newWish);
    }

    // 존재하지 않는 상품 위시에 추가 (직접 추가한 상품)
    @Override
    public Wish createWishFromMyProduct(Long memberId, Product product, WishRequestDTO.CreateWishFromMyProductDTO request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish newWish = WishConverter.toWishMyProduct(member, product, request);
        return wishRepository.save(newWish);
    }

    // 존재하는 상품 위시 수정
    @Override
    public Wish updateWishFromExistingProduct(Long memberId, Long wishId, WishRequestDTO.UpdateWishFromExistingProductDTO request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        wish.setPublicStatus(request.getPublicStatus());
        if (request.getSatisfactionStatus() != null)
            wish.setSatisfactionStatus(request.getSatisfactionStatus());

        return wishRepository.save(wish);
    }


    // 존재하지 않는 상품 위시 수정 (직접 추가한 상품)
    @Override
    public Wish updateWishFromMyProduct(Long memberId, Long wishId, WishRequestDTO.UpdateWishFromMyProductDTO request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        wish.setPublicStatus(request.getPublicStatus());
        if (request.getSatisfactionStatus() != null)
            wish.setSatisfactionStatus(request.getSatisfactionStatus());

        return wishRepository.save(wish);
    }


    // 존재하는 상품 위시 삭제
    @Override
    public Wish deleteWishFromExistingProduct(Long memberId, Long wishId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        wishRepository.delete(wish);

        return wish;
    }

    // 존재하지 않는 상품 위시 삭제 (직접 추가한 상품)
    @Override
    public Wish deleteWishFromMyProduct(Long memberId, Long wishId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        wishRepository.delete(wish);

        return wish;
    }

    // 나의 위시 공개/비공개 설정
    @Override
    public Wish updateWishPublicStatus(Long memberId, Long wishId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        if (wish.getPublicStatus() == PublicStatus.PUBLIC){
            wish.setPublicStatus(PublicStatus.PRIVATE);
        } else {
            wish.setPublicStatus(PublicStatus.PUBLIC);
        }

        return wishRepository.save(wish);
    }

    // 나의 위시 구매 설정
    @Override
    public Wish updateWishPurchaseStatus(Long memberId, Long wishId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.WISH_NOT_FOUND));

        // 이미 구매 상태인 위시 예외처리
        if (wish.getPurchaseStatus() == PurchaseStatus.PURCHASE)
            throw new WishHandler(ErrorStatus.WISH_ALREADY_PURCHASED);

        wish.setPurchaseStatus(PurchaseStatus.PURCHASE);

        return wishRepository.save(wish);
    }

}
