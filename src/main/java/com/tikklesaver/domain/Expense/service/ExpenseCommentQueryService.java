package com.tikklesaver.domain.Expense.service;


import com.tikklesaver.domain.Expense.dto.ExpenseCommentRequestDTO;

public interface ExpenseCommentQueryService {
    // 지출 피드백 삭제
    void deleteExpenseComment(ExpenseCommentRequestDTO.DeleteExpenseCommentRequestDTO request);
}
