package com.tikklesaver.domain.Expense.repository;

import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface ExpenseCommentRepositoryCustom {
    // 지출 피드백 삭제
    Optional<ExpenseComment> findByCommenterIdAndExpenseCommentId(Long commenterId, Long expenseCommentId);

    // 지출 피드백 리스트 조회
    Page<ExpenseComment> findAll(Pageable pageable, Long memberId, Date expenseDate);
}
