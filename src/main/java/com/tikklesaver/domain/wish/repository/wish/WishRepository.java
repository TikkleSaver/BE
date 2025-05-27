package com.tikklesaver.domain.wish.repository.wish;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long>, WishRepositoryCustom {
    int countByMemberId(Long memberId);
}
