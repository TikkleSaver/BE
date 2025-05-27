package com.tikklesaver.domain.member.repository;

import com.tikklesaver.domain.member.entity.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
}
