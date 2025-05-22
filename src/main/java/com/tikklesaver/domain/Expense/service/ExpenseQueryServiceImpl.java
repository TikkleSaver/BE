package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.repository.ExpenseRepository;
import com.tikklesaver.domain.Expense.repository.ExpenseRepositoryCustom;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.ExpenseHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.common.Uuid;
import com.tikklesaver.global.repository.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseQueryServiceImpl implements ExpenseQueryService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;
    private final ExpenseRepositoryCustom expenseRepositoryCustom;

    // 지출 조회
    @Override
    @Transactional
    public Expense getExpense(Long memberId, Long expenseId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_NOT_FOUND));

        return expenseRepositoryCustom.findByMemberIdAndExpenseId(memberId, expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_AND_MEMBER_NOT_FOUND));
    }


    // 지출 삭제
    @Override
    @Transactional
    public void deleteExpense(Long memberId, Long expenseId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_NOT_FOUND));

        Expense expense = expenseRepositoryCustom.findByMemberIdAndExpenseId(memberId, expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_AND_MEMBER_NOT_FOUND));

        amazonS3Manager.deleteFile(expense.getImage());
        expenseRepository.delete(expense);
    }
}
