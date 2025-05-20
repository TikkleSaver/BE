package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;

public interface ExpenseCommandService {

    // 지출 생성
    Expense addExpense(Long memberId, ExpenseRequestDTO.CreateExpenseRequestDTO requestDTO);
}
