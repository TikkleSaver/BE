package com.tikklesaver.domain.Expense.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

public class ExpenseResponseDTO {

    // 지출 생성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateExpenseResultDTO {
        Long expenseId;
        Long memberId;
        String expenseName;
        String expensePlace;
        Long cost;
        Date expenseDate;
        String image;
        Long categoryId;
        LocalDateTime createdAt;
    }

    // 지출 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateExpenseResultDTO {
        Long expenseId;
        Long memberId;
        String expenseName;
        String expensePlace;
        Long cost;
        Date expenseDate;
        String image;
        Long categoryId;
        LocalDateTime updatedAt;
    }
}
