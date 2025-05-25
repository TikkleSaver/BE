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
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    // 지출 피드백 삭제
    @DeleteMapping
    @Operation(summary = "지출 피드백 삭제 API")
    public ApiResponse<String> deleteExpenseComment(
            @RequestBody @Valid ExpenseCommentRequestDTO.DeleteExpenseCommentRequestDTO request) {

        expenseCommentQueryService.deleteExpenseComment(request);
        return ApiResponse.onSuccess("지출 피드백 삭제가 완료되었습니다.");
    }

    // 지출 피드백 리스트 조회
    @GetMapping
    @Operation(summary = "지출 피드백 리스트 조회 API")
    public ApiResponse<ExpenseCommentResponseDTO.ExpenseCommentListResultDTO> getExpenseCommentList(@RequestParam(name = "page") Integer page,
                                                                                      @RequestParam(name = "memberId") Long memberId,
                                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expenseDate
    ){
        Page<ExpenseComment> expenseCommentList = expenseCommentQueryService.getExpenseCommentList(page - 1, memberId, expenseDate);
        return ApiResponse.onSuccess(ExpenseCommentConverter.toGetExpenseCommentResultListDTO(expenseCommentList));
    }

}
