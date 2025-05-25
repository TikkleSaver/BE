package com.tikklesaver.domain.Expense.converter;

import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // 지출 수정
    public static ExpenseResponseDTO.UpdateExpenseResultDTO toUpdateExpenseResultDTO(Expense expense) {
        return ExpenseResponseDTO.UpdateExpenseResultDTO.builder()
                .expenseId(expense.getId())
                .memberId(expense.getMember().getId())
                .expenseName(expense.getExpenseName())
                .expensePlace(expense.getExpensePlace())
                .cost(expense.getCost())
                .expenseDate(expense.getExpenseDate())
                .image(expense.getImage())
                .categoryId(expense.getCategory().getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 지출 조회
    public static ExpenseResponseDTO.GetExpenseResultDTO toGetExpenseResultDTO(Expense expense) {
        return ExpenseResponseDTO.GetExpenseResultDTO.builder()
                .expenseId(expense.getId())
                .memberId(expense.getMember().getId())
                .expenseName(expense.getExpenseName())
                .expensePlace(expense.getExpensePlace())
                .cost(expense.getCost())
                .expenseDate(expense.getExpenseDate())
                .image(expense.getImage())
                .categoryId(expense.getCategory().getId())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }

    // 지출 리스트 조회
    public static ExpenseResponseDTO.ExpensePreviewResultDTO expensePreviewDTO(Expense expense) {
        return ExpenseResponseDTO.ExpensePreviewResultDTO.builder()
                .memberId(expense.getMember().getId())
                .expenseId(expense.getId())
                .expenseName(expense.getExpenseName())
                .expensePlace(expense.getExpensePlace())
                .cost(expense.getCost())
                .expenseDate(expense.getExpenseDate())
                .image(expense.getImage())
                .categoryId(expense.getCategory().getId())
                .build();
    }

    public static ExpenseResponseDTO.ExpensePreviewListResultDTO toGetExpenseResultListDTO(Page<Expense> expensePage) {
        List<ExpenseResponseDTO.ExpensePreviewResultDTO> expensePreviewDTOList =
                expensePage.stream().map(ExpenseConverter::expensePreviewDTO).toList();

        return ExpenseResponseDTO.ExpensePreviewListResultDTO.builder()
                .expensePreviewDTOList(expensePreviewDTOList)
                .listSize(expensePage.getSize())
                .totalPage(expensePage.getTotalPages())
                .totalElements(expensePage.getTotalElements())
                .isFirst(expensePage.isFirst())
                .isLast(expensePage.isLast())
                .build();
    }

    // 일별 지출 종 금액 리스트 조회 API
    public static ExpenseResponseDTO.GetDailyExpenseResultDTO dailyExpenseDTO(Date expenseDate, Long totalCost) {
        return ExpenseResponseDTO.GetDailyExpenseResultDTO.builder()
                .totalCost(totalCost)
                .expenseDate(expenseDate)
                .build();
    }

    public static ExpenseResponseDTO.GetDailyExpenseResultDTOList toGetDailyExpenseResultDTO(List<Expense> expenseList, Long memberId) {
        Map<Date, Long> dailyTotalMap = expenseList.stream()
                .collect(Collectors.groupingBy(
                        Expense::getExpenseDate,
                        Collectors.summingLong(Expense::getCost)
                ));

        List<ExpenseResponseDTO.GetDailyExpenseResultDTO> dailyExpenseDTOList = dailyTotalMap.entrySet().stream()
                .map(entry -> dailyExpenseDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(ExpenseResponseDTO.GetDailyExpenseResultDTO::getExpenseDate))
                .collect(Collectors.toList());

        return ExpenseResponseDTO.GetDailyExpenseResultDTOList.builder()
                .memberId(memberId)
                .dailyExpenseDTOList(dailyExpenseDTOList)
                .build();
    }
}
