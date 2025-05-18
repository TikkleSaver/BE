package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;

public interface ExpenseCommandService {
    Expense addExpense(Long memberId, ExpenseRequestDTO.CreateExpenseRequestDTO requestDTO);
}
