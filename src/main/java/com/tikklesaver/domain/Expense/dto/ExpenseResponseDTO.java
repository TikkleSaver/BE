package com.tikklesaver.domain.Expense.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    // 지출 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetExpenseResultDTO {
        Long expenseId;
        Long memberId;
        String expenseName;
        String expensePlace;
        Long cost;
        Date expenseDate;
        String image;
        Long categoryId;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }


    // 지출 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpensePreviewResultDTO {
        Long memberId;
        Long expenseId;
        String expenseName;
        String expensePlace;
        Long cost;
        Date expenseDate;
        String image;
        Long categoryId;
    }

    // 지출 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpensePreviewListResultDTO {
        private List<ExpensePreviewResultDTO> expensePreviewDTOList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}
