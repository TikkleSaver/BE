package com.tikklesaver.domain.Expense.repository;

import com.tikklesaver.domain.Expense.entity.ExpenseComment;

import java.util.Optional;

public interface ExpenseCommentRepositoryCustom {
    // 지출 피드백 삭제
    Optional<ExpenseComment> findByCommenterIdAndExpenseCommentId(Long commenterId, Long expenseCommentId);
}
