package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import org.springframework.web.multipart.MultipartFile;

public interface ExpenseQueryService {
    // 지출 조회
    Expense getExpense(Long memberId, Long expenseId);

    // 지출 삭제
    void deleteExpense(Long memberId, Long expenseId);
}
