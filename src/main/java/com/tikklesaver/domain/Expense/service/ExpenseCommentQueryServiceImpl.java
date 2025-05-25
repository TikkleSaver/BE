package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Expense.dto.ExpenseCommentRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import com.tikklesaver.domain.Expense.repository.ExpenseCommentRepository;
import com.tikklesaver.domain.Expense.repository.ExpenseCommentRepositoryCustom;
import com.tikklesaver.domain.Expense.repository.ExpenseRepository;
import com.tikklesaver.domain.Expense.repository.ExpenseRepositoryCustom;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.ExpenseCommentHandler;
import com.tikklesaver.global.apiPayload.exception.handler.ExpenseHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.repository.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCommentQueryServiceImpl implements ExpenseCommentQueryService {
    private final MemberRepository memberRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final ExpenseCommentRepositoryCustom expenseCommentRepositoryCustom;
    private final ExpenseCommentRepository expenseCommentRepository;

    // 지출 피드백 삭제
    @Override
    @Transactional
    public void deleteExpenseComment(ExpenseCommentRequestDTO.DeleteExpenseCommentRequestDTO request) {
        memberRepository.findById(request.getCommenterId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + request.getCommenterId()));

        expenseCommentRepository.findById(request.getExpenseCommentId())
                .orElseThrow(() -> new ExpenseCommentHandler(ErrorStatus.EXPENSE_COMMENT_NOT_FOUND));

        ExpenseComment expenseComment = expenseCommentRepositoryCustom.findByCommenterIdAndExpenseCommentId(request.getCommenterId(), request.getExpenseCommentId())
                .orElseThrow(() -> new ExpenseCommentHandler(ErrorStatus.EXPENSE_COMMENT_NOT_FOUND));

        expenseCommentRepository.delete(expenseComment);
    }

}
