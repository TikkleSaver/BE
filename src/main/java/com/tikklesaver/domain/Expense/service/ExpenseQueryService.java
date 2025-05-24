package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.entity.Expense;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface ExpenseQueryService {
    // 지출 조회
    Expense getExpense(Long memberId, Long expenseId);

    // 지출 리스트 조회
    Page<Expense> getExpenseList(Integer page, Long memberId, Date expenseDate);

    // 지출 삭제
    void deleteExpense(Long memberId, Long expenseId);
}
