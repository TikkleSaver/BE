package com.tikklesaver.domain.Expense.service;


import com.tikklesaver.domain.Expense.dto.ExpenseCommentRequestDTO;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface ExpenseCommentQueryService {
    // 지출 피드백 삭제
    void deleteExpenseComment(ExpenseCommentRequestDTO.DeleteExpenseCommentRequestDTO request);

    // 지출 피드백 리스트 조회
    Page<ExpenseComment> getExpenseCommentList(Integer page, Long memberId, Date expenseDate);
}
