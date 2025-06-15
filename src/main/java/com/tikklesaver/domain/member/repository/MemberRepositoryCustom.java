package com.tikklesaver.domain.member.repository;

import com.tikklesaver.domain.member.entity.Member;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> searchByNicknameKeyword(String keyword, Pageable pageable);
}


