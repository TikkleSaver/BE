package com.tikklesaver.domain.Expense.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ExpenseCommentRequestDTO {

    // 지출 피드백 생성
    @Setter
    @Getter
    public static class CreateExpenseCommentRequestDTO {
        @NotNull
        Long memberId;
        @NotEmpty
        String content;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date expenseDate;
    }

    // 지출 피드백 수정
    @Setter
    @Getter
    public static class UpdateExpenseCommentRequestDTO {
        @NotNull
        Long expenseCommentId;
        @NotNull
        Long memberId;
        @NotEmpty
        String content;
    }

    // 지출 피드백 삭제
    @Setter
    @Getter
    public static class DeleteExpenseCommentRequestDTO {
        @NotNull
        Long expenseCommentId;
        @NotNull
        Long memberId;
    }
}
