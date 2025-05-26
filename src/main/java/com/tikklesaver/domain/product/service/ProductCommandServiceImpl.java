package com.tikklesaver.domain.product.service;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.product.converter.ProductConverter;
import com.tikklesaver.domain.product.entity.Product;
import com.tikklesaver.domain.product.repository.ProductRepository;
import com.tikklesaver.domain.wish.dto.wish.WishRequestDTO;
import com.tikklesaver.domain.wish.entity.enums.ProductType;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.WishHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.common.Uuid;
import com.tikklesaver.global.repository.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;

    // 존재하는 상품 추가
    @Override
    public Product createExistingProduct(Member member, WishRequestDTO.CreateWishFromExistingProductDTO request){

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        Product newProduct = ProductConverter.toProduct(member, request, category);

        return productRepository.save(newProduct);
    }

    // 존재하지 않는 상품 추가
    @Override
    public Product createMyProduct(Member member, WishRequestDTO.CreateWishFromMyProductDTO request, MultipartFile file){

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        
        // 이미지 저장
        String imageUrl = null;
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());
        if (file != null && !file.isEmpty()) {  // 이미지 필수
            imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateProductsKeyName(savedUuid),file);
        }

        Product newProduct = ProductConverter.toMyProduct(member, request, category, imageUrl);

        return productRepository.save(newProduct);
    }

    // 존재하는 상품 수정
    @Override
    public Product updateExistingProduct(Member member, Product product, WishRequestDTO.UpdateWishFromExistingProductDTO request){

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        if (product.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

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

    // 존재하지 않는 상품 수정 (직접 추가한)
    @Override
    public Product updateMyProduct(Member member, Product product, WishRequestDTO.UpdateWishFromMyProductDTO request, MultipartFile file){

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        if (product.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        if (file != null && !file.isEmpty()) {  // 이미지도 수정할 시
            // 이전 이미지 삭제
            amazonS3Manager.deleteFile(product.getImage());
            // 새로운 이미지 저장
            String imageUrl = null;
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(uuid).build());
            imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateProductsKeyName(savedUuid), file);

            product.setImage(imageUrl);
        }

        product.setPrice(request.getPrice());
        product.setCategory(category);

        return productRepository.save(product);
    }

    // 상품 삭제
    @Override
    public void deleteProduct(Member member, Product product){

        if (product.getMember() != member)
            throw new WishHandler(ErrorStatus.WISH_NOT_AUTHOR);

        // s3 이미지 삭제
        if (product.getProductType() == ProductType.MYPRODUCT){
            amazonS3Manager.deleteFile(product.getImage());
        }

        productRepository.delete(product);
    }
}
