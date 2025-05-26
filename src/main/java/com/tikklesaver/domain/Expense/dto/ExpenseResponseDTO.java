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


    // 일별 지출 총액 조회 - 하루
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDailyExpenseResultDTO {
        Long totalCost;
        Date expenseDate;
    }

    // 일별 지출 총액 조회 - 한달(하루 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDailyExpenseResultDTOList {
        Long memberId;
        List<GetDailyExpenseResultDTO> dailyExpenseDTOList;
    }

    // 월별 지출 총액 조회 - 한달
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyExpenseTotalDTO {
        int month;
        Long totalAmount;
    }

    // 월별 지출 총액 조회 - 1년(한달 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMonthlyExpenseResultDTOList {
        Long memberId;
        List<MonthlyExpenseTotalDTO> monthlyExpenseDTOList;
    }

    // 카테고리별 지출 총액 조회 - 카테고리 하나
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalExpenseByCategoryResultDTO {
        Long categoryId;
        Long totalAmount;
    }

    // 카테고리별 지출 총액 조회 - (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTotalExpenseByCategoryResultDTOList {
        Long memberId;
        int year;
        int month;
        List<TotalExpenseByCategoryResultDTO> categoryExpenseList;
    }
}
