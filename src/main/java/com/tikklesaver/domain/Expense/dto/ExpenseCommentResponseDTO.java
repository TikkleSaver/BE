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
import java.util.List;

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

    // 지출 피드백 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseCommentResultDTO {
        Long memberId;
        Long commenterId;
        Long expenseCommentId;
        String nickname;
        String profileUrl;
        String content;
        Date expenseDate;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

    // 지출 피드백 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseCommentListResultDTO {
        private List<ExpenseCommentResponseDTO.ExpenseCommentResultDTO> expenseCommentDTOList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}
