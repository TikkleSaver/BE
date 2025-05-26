package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseCommentRequestDTO;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import org.springframework.web.multipart.MultipartFile;

public interface ExpenseCommentCommandService {
    // 지출 피드백 생성
    ExpenseComment addExpenseComment(Long commenterId, ExpenseCommentRequestDTO.CreateExpenseCommentRequestDTO requestDTO);

    // 지출 피드백 수정
    ExpenseComment updateExpenseComment(Long commenterId, ExpenseCommentRequestDTO.UpdateExpenseCommentRequestDTO requestDTO);
}
