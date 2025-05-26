package com.tikklesaver.domain.wish.repository.wishComment;

import com.tikklesaver.domain.wish.entity.WishComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishCommentRepository extends JpaRepository<WishComment, Long>, WishCommentRepositoryCustom {

}
