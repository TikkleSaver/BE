package com.tikklesaver.domain.Expense.repository;

import com.tikklesaver.domain.Expense.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface ExpenseRepositoryCustom {
    Optional<Expense> findByMemberIdAndExpenseId(Long memberId, Long expenseId);
    Page<Expense> findAll(Pageable pageable, Long memberId, Date expenseDate);
}
