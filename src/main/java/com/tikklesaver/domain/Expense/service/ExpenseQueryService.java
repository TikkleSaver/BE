package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ExpenseQueryService {
    // 지출 조회
    Expense getExpense(Long memberId, Long expenseId);

    // 지출 리스트 조회
    Page<Expense> getExpenseList(Member viewer, Integer page, Long memberId, Date expenseDate);

    // 지출 삭제
    void deleteExpense(Long memberId, Long expenseId);

    // 지출 수정
    Expense updateExpense(Long memberId, ExpenseRequestDTO.UpdateExpenseRequestDTO requestDTO, MultipartFile file);

    // 일별 지출 총액 리스트 조회
    List<Expense> getDailyExpense(Member viewer, Long memberId, int year, int month);

    // 특정 년도의 월별 지출 총 금액 리스트 조회
    List<ExpenseResponseDTO.MonthlyExpenseTotalDTO> getMonthlyExpense(Long memberId, int year);

    // 특정 사용자의 특정 달의 카테고리별 지출 금액 리스트 조회
    List<ExpenseResponseDTO.TotalExpenseByCategoryResultDTO> getTotalExpenseByCategory(Long memberId, int year, int month);

    // 특정 사용자의 특정 달 지출 총 금액 조회
    Long getTotalExpenseByMonth(Long memberId, int year, int month);
}
