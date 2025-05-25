package com.tikklesaver.domain.member.repository;

import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNickname(String nickname);

    String findRefreshTokenByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
