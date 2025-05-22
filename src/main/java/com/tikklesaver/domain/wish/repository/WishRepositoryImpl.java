package com.tikklesaver.domain.wish.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.tikklesaver.domain.product.entity.QProduct;
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

    // 위시리스트 상세 조회
    @Override
    public WishResponseDTO.WishDetailDTO getWishDetail(Long wishId){
        return jpaQueryFactory
                .select(Projections.constructor(
                        WishResponseDTO.WishDetailDTO.class,
                        wish.id,
                        wish.member.id,
                        wish.member.nickname,
                        wish.product.category.id,
                        wish.product.title,
                        wish.product.brand,
                        wish.product.price,
                        wish.product.image,
                        wish.publicStatus,
                        wish.satisfactionStatus,
                        wish.purchaseStatus,
                        wish.product.productType,
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
                .where(wish.id.eq(wishId))
                .fetchOne();
    }

    // 나의 위시리스트 목록 구매 예정 조회
    @Override
    public List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Long memberId) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        WishResponseDTO.MyWishPlannedPreviewDTO.class,
                        wish.id,
                        wish.member.id,
                        wish.member.nickname,
                        wish.product.title,
                        wish.product.price,
                        wish.product.image,
                        wish.publicStatus,
                        wish.product.productType,
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
                .where(
                        wish.member.id.eq(memberId),
                        wish.purchaseStatus.eq(PurchaseStatus.PLANNED)
                )
                .orderBy(wish.createdAt.desc())
                .fetch();
    }

    // 나의 위시리스트 목록 구매 완료 조회
    @Override
    public List<WishResponseDTO.MyWishPurchasedPreviewDTO> getMyWishPurchasedList(Long memberId) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        WishResponseDTO.MyWishPurchasedPreviewDTO.class,
                        wish.id,
                        wish.member.id,
                        wish.member.nickname,
                        wish.product.title,
                        wish.product.price,
                        wish.product.image,
                        wish.publicStatus,
                        wish.satisfactionStatus,
                        wish.product.productType,
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
                .where(
                        wish.member.id.eq(memberId),
                        wish.purchaseStatus.eq(PurchaseStatus.PURCHASE)
                )
                .orderBy(wish.createdAt.desc())
                .fetch();
    }
}
