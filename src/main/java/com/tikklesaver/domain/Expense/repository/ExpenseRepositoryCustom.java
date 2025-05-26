package com.tikklesaver.domain.Expense.repository;

import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryCustom {
    Optional<Expense> findByMemberIdAndExpenseId(Long memberId, Long expenseId);
    Page<Expense> findAll(Pageable pageable, Long memberId, Date expenseDate);
    List<Expense> findDailyExpenseTotalByMemberIdAndYearMonth(Long memberId, int year, int month);
    List<ExpenseResponseDTO.MonthlyExpenseTotalDTO> findMonthlyExpenseTotalByMemberIdAndYear(Long memberId, int year);
    List<ExpenseResponseDTO.TotalExpenseByCategoryResultDTO> findExpenseTotalByMemberIdAndYearMonthCategory(Long memberId, int year, int month);
}
