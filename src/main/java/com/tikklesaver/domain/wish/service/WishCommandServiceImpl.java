package com.tikklesaver.domain.wish.service;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.wish.converter.WishConverter;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.repository.WishRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishCommandServiceImpl implements WishCommandService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;

    // 존재하는 상품 위시에 추가 @Override
    public Wish CreateWishFromExistingProduct(Long memberId, Product product, WishRequestDTO.CreateWishFromExistingProductDTO request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Wish newWish = WishConverter.toWish(member, product, request);
        return wishRepository.save(newWish);
    }
}
