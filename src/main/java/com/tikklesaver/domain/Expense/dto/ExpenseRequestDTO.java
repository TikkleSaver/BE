package com.tikklesaver.domain.Expense.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

public class ExpenseRequestDTO {

    // 지출 생성
    @Setter
    @Getter
    public static class CreateExpenseRequestDTO {
        @NotNull
        Long memberId;
        @NotEmpty
        String expenseName;
        @NotEmpty
        String expensePlace;
        @NotNull
        Long cost;
        @NotNull
        Date expenseDate;
        String image;
        @NotNull
        Long categoryId;
    }
}
