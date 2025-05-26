package com.tikklesaver.domain.Expense.converter;

import com.tikklesaver.domain.Expense.dto.ExpenseCommentResponseDTO;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

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

    // 지출 피드백 리스트 조회
    public static ExpenseCommentResponseDTO.ExpenseCommentResultDTO expenseCommentResultDTO(ExpenseComment expenseComment) {
        return ExpenseCommentResponseDTO.ExpenseCommentResultDTO.builder()
                .memberId(expenseComment.getMember().getId())
                .commenterId(expenseComment.getCommenter().getId())
                .expenseCommentId(expenseComment.getId())
                .nickname(expenseComment.getCommenter().getNickname())
                .profileUrl(expenseComment.getCommenter().getProfileUrl())
                .content(expenseComment.getContent())
                .expenseDate(expenseComment.getExpenseDate())
                .createdAt(expenseComment.getCreatedAt())
                .updatedAt(expenseComment.getUpdatedAt())
                .build();
    }

    public static  ExpenseCommentResponseDTO.ExpenseCommentListResultDTO toGetExpenseCommentResultListDTO(Page<ExpenseComment> expenseCommentPage) {
        List<ExpenseCommentResponseDTO.ExpenseCommentResultDTO> expenseCommentResultDTOList =
                expenseCommentPage.stream().map(ExpenseCommentConverter::expenseCommentResultDTO).toList();

        return ExpenseCommentResponseDTO.ExpenseCommentListResultDTO.builder()
                .expenseCommentDTOList(expenseCommentResultDTOList)
                .listSize(expenseCommentPage.getSize())
                .totalPage(expenseCommentPage.getTotalPages())
                .totalElements(expenseCommentPage.getTotalElements())
                .isFirst(expenseCommentPage.isFirst())
                .isLast(expenseCommentPage.isLast())
                .build();
    }
}
