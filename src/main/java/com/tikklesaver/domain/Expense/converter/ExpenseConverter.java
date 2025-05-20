package com.tikklesaver.domain.Expense.converter;

import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;

import java.time.LocalDateTime;

public class ExpenseConverter {

    // 지출 생성
    public static ExpenseResponseDTO.CreateExpenseResultDTO toExpenseResultDTO(Expense expense) {
        return ExpenseResponseDTO.CreateExpenseResultDTO.builder()
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
