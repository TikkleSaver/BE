package com.tikklesaver.domain.wish.repository.wishComment;

import com.tikklesaver.domain.wish.entity.Wish;
import com.tikklesaver.domain.wish.entity.WishComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishCommentRepository extends JpaRepository<WishComment, Long>, WishCommentRepositoryCustom {
    
    // 위시ID로 위시 댓글 찾기
    List<WishComment> findAllByWishOrderByCreatedAt(Wish wish);
}
