package com.tikklesaver.domain.Expense.converter;

import com.tikklesaver.domain.Expense.dto.ExpenseCommentResponseDTO;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;

import java.time.LocalDateTime;

public class ExpenseCommentConverter {
    // 지출 피드백 생성
    public static ExpenseCommentResponseDTO.CreateExpenseCommentResultDTO toExpenseCommentResultDTO(ExpenseComment expenseComment) {
        return ExpenseCommentResponseDTO.CreateExpenseCommentResultDTO.builder()
                .expenseCommentId(expenseComment.getId())
                .memberId(expenseComment.getMember().getId())
                .commenterId(expenseComment.getCommenter().getId())
                .nickname(expenseComment.getCommenter().getNickname())
                .profileUrl(expenseComment.getCommenter().getProfileUrl())
                .content(expenseComment.getContent())
                .expenseDate(expenseComment.getExpenseDate())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 지출 피드백 수정
    public static ExpenseCommentResponseDTO.UpdateExpenseCommentResultDTO toUpdateExpenseCommentResultDTO(ExpenseComment expenseComment) {
        return ExpenseCommentResponseDTO.UpdateExpenseCommentResultDTO.builder()
                .expenseCommentId(expenseComment.getId())
                .memberId(expenseComment.getMember().getId())
                .commenterId(expenseComment.getCommenter().getId())
                .nickname(expenseComment.getCommenter().getNickname())
                .profileUrl(expenseComment.getCommenter().getProfileUrl())
                .content(expenseComment.getContent())
                .expenseDate(expenseComment.getExpenseDate())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
