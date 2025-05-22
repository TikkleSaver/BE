package com.tikklesaver.domain.Expense.repository;

import com.tikklesaver.domain.Expense.entity.Expense;

import java.util.Optional;

public interface ExpenseRepositoryCustom {
    Optional<Expense> findByMemberIdAndExpenseId(Long memberId, Long expenseId);
}
