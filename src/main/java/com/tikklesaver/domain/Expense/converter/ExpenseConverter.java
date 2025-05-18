package com.tikklesaver.domain.Expense.converter;

import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;

import java.time.LocalDateTime;

public class ExpenseConverter {
    public static ExpenseResponseDTO.CreateExpenseRequestResultDTO toExpenseResultDTO(Expense expense) {
        return ExpenseResponseDTO.CreateExpenseRequestResultDTO.builder()
                .expenseId(expense.getId())
                .memberId(expense.getMember().getId())
                .expenseName(expense.getExpenseName())
                .expensePlace(expense.getExpensePlace())
                .cost(expense.getCost())
                .expenseDate(expense.getExpenseDate())
                .image(expense.getImage())
                .categoryId(expense.getCategory().getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
