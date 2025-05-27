package com.tikklesaver.domain.member.repository;

import com.tikklesaver.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> searchByNicknameKeyword(String keyword);
}

