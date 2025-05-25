package com.tikklesaver.domain.wish.repository.vote;

import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.wish.entity.Vote;
import com.tikklesaver.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long>, VoteRepositoryCustom {
    
    // 투표 존재 유무
    Optional<Vote> findByMemberAndWish(Member member, Wish wish);
}
