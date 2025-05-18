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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseQueryService expenseQueryService;
    private final ExpenseCommandService expenseCommandService;

    @PostMapping
    @Operation(summary = "지출 생성 API")
    public ApiResponse<ExpenseResponseDTO.CreateExpenseRequestResultDTO> addExpense(
            @RequestBody @Valid ExpenseRequestDTO.CreateExpenseRequestDTO request) {

        //임시 memberId
        Long memberId = 1L;
        Expense expense = expenseCommandService.addExpense(memberId, request);
        return ApiResponse.onSuccess(ExpenseConverter.toExpenseResultDTO(expense));
    }
}
