package com.tikklesaver.domain.Expense.controller;

import com.tikklesaver.domain.Expense.converter.ExpenseCommentConverter;
import com.tikklesaver.domain.Expense.dto.ExpenseCommentRequestDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseCommentResponseDTO;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import com.tikklesaver.domain.Expense.service.ExpenseCommentCommandService;
import com.tikklesaver.domain.Expense.service.ExpenseCommentQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenseComment")
public class ExpenseCommentController {
    private final ExpenseCommentQueryService expenseCommentQueryService;
    private final ExpenseCommentCommandService expenseCommentCommandService;


    // 지출 피드백 생성
    @PostMapping
    @Operation(summary = "지출 피드백 생성 API")
    public ApiResponse<ExpenseCommentResponseDTO.CreateExpenseCommentResultDTO> addExpenseComment(
            @RequestBody @Valid ExpenseCommentRequestDTO.CreateExpenseCommentRequestDTO request) {

        ExpenseComment expenseComment = expenseCommentCommandService.addExpenseComment(request);
        return ApiResponse.onSuccess(ExpenseCommentConverter.toExpenseCommentResultDTO(expenseComment));
    }

    // 지출 피드백 수정
    @PatchMapping
    @Operation(summary = "지출 피드백 수정 API")
    public ApiResponse<ExpenseCommentResponseDTO.UpdateExpenseCommentResultDTO> updateExpenseComment(
            @RequestBody @Valid ExpenseCommentRequestDTO.UpdateExpenseCommentRequestDTO request) {

        ExpenseComment expenseComment = expenseCommentCommandService.updateExpenseComment(request);
        return ApiResponse.onSuccess(ExpenseCommentConverter.toUpdateExpenseCommentResultDTO(expenseComment));
    }
}
