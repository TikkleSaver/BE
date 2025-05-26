package com.tikklesaver.domain.Expense.service;

import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
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

    // 멤버 ID 검증 함수
    private void findById(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));
    }

    // 지출 조회
    @Override
    @Transactional
    public Expense getExpense(Long memberId, Long expenseId) {
        this.findById(memberId);

        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_NOT_FOUND));

        return expenseRepositoryCustom.findByMemberIdAndExpenseId(memberId, expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_AND_MEMBER_NOT_FOUND));
    }

    // 지출 리스트 조회
    @Override
    @Transactional
    public Page<Expense> getExpenseList(Member viewer, Integer page, Long memberId, Date expenseDate) {
        this.findById(viewer.getId());
        this.findById(memberId);

        Pageable pageable = PageRequest.of(page, 9);

        return expenseRepositoryCustom.findAll(pageable, memberId, expenseDate);
    }

    // 지출 삭제
    @Override
    @Transactional
    public void deleteExpense(Long memberId, Long expenseId) {
        this.findById(memberId);

        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_NOT_FOUND));

        Expense expense = expenseRepositoryCustom.findByMemberIdAndExpenseId(memberId, expenseId)
                .orElseThrow(() -> new ExpenseHandler(ErrorStatus.EXPENSE_AND_MEMBER_NOT_FOUND));

        if (expense.getImage() != null) {
            amazonS3Manager.deleteFile(expense.getImage());
        }

        expenseRepository.delete(expense);
    }

    // 지출 수정
    @Override
    @Transactional
    public  Expense updateExpense(Long memberId, ExpenseRequestDTO.UpdateExpenseRequestDTO requestDTO, MultipartFile file) {
        this.findById(memberId);

        Expense expense = expenseRepositoryCustom.findByMemberIdAndExpenseId(memberId, requestDTO.getExpenseId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 지출을 찾을 수 없습니다. ID: " + requestDTO.getExpenseId()));

        String imageUrl = expense.getImage();
        if (file != null && !file.isEmpty()) {
            if (imageUrl != null) {
                amazonS3Manager.deleteFile(imageUrl);
            }

            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(uuid)
                    .build());

            imageUrl = amazonS3Manager.uploadFile(
                    amazonS3Manager.generateExpensesKeyName(savedUuid),
                    file
            );
        }

        if (requestDTO.getExpenseName() != null) expense.setExpenseName(requestDTO.getExpenseName());
        if (requestDTO.getExpensePlace() != null) expense.setExpensePlace(requestDTO.getExpensePlace());
        if (requestDTO.getCost() != null) expense.setCost(requestDTO.getCost());
        if (requestDTO.getExpenseDate() != null) expense.setExpenseDate(requestDTO.getExpenseDate());
        if (requestDTO.getCategoryId() != null)
            expense.setCategory(categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("해당하는 Category를 찾을 수 없습니다. ID: " + requestDTO.getCategoryId()))
            );
        if (imageUrl != null) expense.setImage(imageUrl);

        return expenseRepository.save(expense);
    }

    // 일별 지출 총액 리스트 조회
    @Override
    @Transactional
    public List<Expense> getDailyExpense(Member viewer, Long memberId, int year, int month) {
        this.findById(viewer.getId());
        this.findById(memberId);

        return expenseRepositoryCustom.findDailyExpenseTotalByMemberIdAndYearMonth(memberId, year, month);
    }

    // 특정 년도의 월별 지출 총 금액 리스트 조회
    @Override
    @Transactional
    public List<ExpenseResponseDTO.MonthlyExpenseTotalDTO> getMonthlyExpense(Long memberId, int year) {
        this.findById(memberId);
        return expenseRepositoryCustom.findMonthlyExpenseTotalByMemberIdAndYear(memberId, year);
    }

    // 특정 사용자의 특정 달의 카테고리별 지출 금액 리스트 조회
    @Override
    @Transactional
    public List<ExpenseResponseDTO.TotalExpenseByCategoryResultDTO> getTotalExpenseByCategory(Long memberId, int year, int month){
        this.findById(memberId);
        return expenseRepositoryCustom.findExpenseTotalByMemberIdAndYearMonthCategory(memberId, year, month);
    }
}
