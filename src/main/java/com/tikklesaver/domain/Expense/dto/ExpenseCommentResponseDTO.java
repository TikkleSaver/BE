package com.tikklesaver.domain.Expense.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class ExpenseCommentResponseDTO {

    // 지출 피드백 생성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateExpenseCommentResultDTO {
        Long expenseCommentId;
        Long memberId;
        Long commenterId;
        String nickname;
        String profileUrl;
        String content;
        Date expenseDate;
        LocalDateTime createdAt;
    }

    // 지출 피드백 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateExpenseCommentResultDTO {
        Long expenseCommentId;
        Long memberId;
        Long commenterId;
        String nickname;
        String profileUrl;
        String content;
        Date expenseDate;
        LocalDateTime updatedAt;
    }
}
