package com.tikklesaver.domain.Expense.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date expenseDate;
        @NotNull
        Long categoryId;
    }

    // 지출 수정
    @Setter
    @Getter
    public static class UpdateExpenseRequestDTO {
        @NotNull
        Long memberId;
        @NotNull
        Long expenseId;
        String expenseName;
        String expensePlace;
        Long cost;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date expenseDate;
        @NotNull
        Long categoryId;
    }
}
