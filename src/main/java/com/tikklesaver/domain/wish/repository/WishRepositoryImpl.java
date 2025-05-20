package com.tikklesaver.domain.wish.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.tikklesaver.domain.wish.dto.WishResponseDTO;
import com.tikklesaver.domain.wish.entity.QVote;
import com.tikklesaver.domain.wish.entity.QWish;
import com.tikklesaver.domain.wish.entity.QWishComment;
import com.tikklesaver.domain.wish.entity.enums.LikeStatus;
import com.tikklesaver.domain.wish.entity.enums.ProductType;
import com.tikklesaver.domain.wish.entity.enums.PurchaseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QWish wish = QWish.wish;
    private final QVote vote = QVote.vote;
    private final QWishComment wishComment = QWishComment.wishComment;

    // 나의 위시리스트 목록 구매 예정 조회
    @Override
    public List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Long memberId) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        WishResponseDTO.MyWishPlannedPreviewDTO.class,
                        wish.id,
                        wish.member.id,
                        wish.member.nickname,
                        new CaseBuilder()
                                .when(wish.productType.eq(ProductType.PRODUCT))
                                .then(wish.product.title)
                                .otherwise(wish.myProduct.title),
                        new CaseBuilder()
                                .when(wish.productType.eq(ProductType.PRODUCT))
                                .then(wish.product.price)
                                .otherwise(wish.myProduct.price),
                        new CaseBuilder()
                                .when(wish.productType.eq(ProductType.PRODUCT))
                                .then(wish.product.image)
                                .otherwise(wish.myProduct.image),
                        wish.publicStatus,
                        wish.productType,
                        JPAExpressions.select(vote.count())
                                .from(vote)
                                .where(vote.wish.id.eq(wish.id),
                                        vote.likeStatus.eq(LikeStatus.LIKE)),
                        JPAExpressions.select(vote.count())
                                .from(vote)
                                .where(vote.wish.id.eq(wish.id),
                                        vote.likeStatus.eq(LikeStatus.UNLIKE)),
                        JPAExpressions.select(wishComment.count())
                                .from(wishComment)
                                .where(wishComment.wish.id.eq(wish.id)),
                        wish.createdAt
                ))
                .from(wish)
                .leftJoin(wish.product)
                .leftJoin(wish.myProduct)
                .where(
                        wish.member.id.eq(memberId),
                        wish.purchaseStatus.eq(PurchaseStatus.PLANNED)
                )
                .orderBy(wish.createdAt.desc())
                .fetch();
    }
}
