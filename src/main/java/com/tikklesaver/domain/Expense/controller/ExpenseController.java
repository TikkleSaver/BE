package com.tikklesaver.domain.Expense.controller;

import com.tikklesaver.domain.Expense.converter.ExpenseConverter;
import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.service.ExpenseCommandService;
import com.tikklesaver.domain.Expense.service.ExpenseQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseQueryService expenseQueryService;
    private final ExpenseCommandService expenseCommandService;


    // 지출 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지출 생성 API")
    public ApiResponse<ExpenseResponseDTO.CreateExpenseResultDTO> addExpense(
            @RequestPart("request") @Valid ExpenseRequestDTO.CreateExpenseRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Long memberId = 1L;
        Expense expense = expenseCommandService.addExpense(memberId, request, file);
        return ApiResponse.onSuccess(ExpenseConverter.toExpenseResultDTO(expense));
    }

    // 지출 수정
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지출 수정 API")
    public ApiResponse<ExpenseResponseDTO.UpdateExpenseResultDTO> updateExpense(
            @RequestPart("request") @Valid ExpenseRequestDTO.UpdateExpenseRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Long memberId = 1L;
        Expense expense = expenseCommandService.updateExpense(memberId, request, file);
        return ApiResponse.onSuccess(ExpenseConverter.toUpdateExpenseResultDTO(expense));
    }
}
