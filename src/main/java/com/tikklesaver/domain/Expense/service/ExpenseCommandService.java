package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import org.springframework.web.multipart.MultipartFile;

public interface ExpenseCommandService {

    // 지출 생성
    Expense addExpense(Long memberId, ExpenseRequestDTO.CreateExpenseRequestDTO requestDTO, MultipartFile file);

    // 지출 수정
    Expense updateExpense(Long memberId, ExpenseRequestDTO.UpdateExpenseRequestDTO requestDTO, MultipartFile file);
}
