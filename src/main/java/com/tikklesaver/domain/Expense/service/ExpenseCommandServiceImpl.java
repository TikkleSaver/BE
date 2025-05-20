package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.repository.ExpenseRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
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
public class ExpenseCommandServiceImpl implements ExpenseCommandService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;

    // 지출 생성
    @Override
    @Transactional
    public  Expense addExpense(Long memberId, ExpenseRequestDTO.CreateExpenseRequestDTO requestDTO, MultipartFile file) {


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 Category를 찾을 수 없습니다. ID: " + requestDTO.getCategoryId()));

        String imageUrl = null;

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        if (file != null && !file.isEmpty()) {
            imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateExpensesKeyName(savedUuid),file);
        }

        Expense expense = Expense.builder()
                .member(member)
                .expenseName(requestDTO.getExpenseName())
                .expensePlace(requestDTO.getExpensePlace())
                .cost(requestDTO.getCost())
                .expenseDate(requestDTO.getExpenseDate())
                .image(imageUrl)
                .category(category)
                .build();

        return expenseRepository.save(expense);
    }
}
