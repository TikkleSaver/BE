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

    @Override
    public Product createProduct(Long memberId, WishRequestDTO.CreateWishFromExistingProductDTO request){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        Product newProduct = ProductConverter.toProduct(member, request, category);

        return productRepository.save(newProduct);
    }
}
