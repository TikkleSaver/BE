package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Expense.dto.ExpenseCommentRequestDTO;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import com.tikklesaver.domain.Expense.repository.ExpenseCommentRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.ExpenseCommentHandler;
import com.tikklesaver.global.apiPayload.exception.handler.ExpenseHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.repository.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ExpenseCommentCommandServiceImpl implements ExpenseCommentCommandService {
    private final MemberRepository memberRepository;
    private final ExpenseCommentRepository expenseCommentRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;

    // 지출 피드백 생성
    @Override
    @Transactional
    public ExpenseComment addExpenseComment(ExpenseCommentRequestDTO.CreateExpenseCommentRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + requestDTO.getMemberId()));

        Member commenter = memberRepository.findById(requestDTO.getCommenterId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + requestDTO.getCommenterId()));


        ExpenseComment expenseComment = ExpenseComment.builder()
                .member(member)
                .content(requestDTO.getContent())
                .expenseDate(requestDTO.getExpenseDate())
                .commenter(commenter)
                .build();

        return expenseCommentRepository.save(expenseComment);
    }

    // 지출 피드백 수정
    @Override
    @Transactional
    public ExpenseComment updateExpenseComment(ExpenseCommentRequestDTO.UpdateExpenseCommentRequestDTO requestDTO) {
        memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + requestDTO.getMemberId()));

        memberRepository.findById(requestDTO.getCommenterId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + requestDTO.getCommenterId()));

        ExpenseComment expenseComment = expenseCommentRepository.findById(requestDTO.getExpenseCommentId())
                .orElseThrow(() -> new ExpenseCommentHandler(ErrorStatus.EXPENSE_COMMENT_NOT_FOUND));

        if (requestDTO.getContent() != null) expenseComment.setContent(requestDTO.getContent());

        return expenseCommentRepository.save(expenseComment);
    }
}
