package com.tikklesaver.domain.product.service;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.product.converter.ProductConverter;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.product.repository.ProductRepository;
import com.tikklesaver.domain.wish.dto.WishRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // 존재하는 상품 추가
    @Override
    public Product createExistingProduct(Long memberId, WishRequestDTO.CreateWishFromExistingProductDTO request){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        Product newProduct = ProductConverter.toProduct(member, request, category);

        return productRepository.save(newProduct);
    }

    // 존재하는 상품 수정
    @Override
    public Product updateExistingProduct(Long memberId, Product product, WishRequestDTO.UpdateWishFromExistingProductDTO request){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        product.setPrice(request.getPrice());
        product.setCategory1(request.getCategory1());
        if (request.getCategory2() != null)
            product.setCategory2(request.getCategory2());
        if (request.getCategory3() != null)
            product.setCategory3(request.getCategory3());
        if (request.getCategory4() != null)
            product.setCategory4(request.getCategory4());
        product.setCategory(category);

        return productRepository.save(product);
    }
}
