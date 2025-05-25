package com.tikklesaver.domain.Expense.controller;

import com.tikklesaver.domain.Expense.converter.ExpenseConverter;
import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.service.ExpenseCommandService;
import com.tikklesaver.domain.Expense.service.ExpenseQueryService;
import com.tikklesaver.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameters;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseQueryService expenseQueryService;
    private final ExpenseCommandService expenseCommandService;

    // 지출 조회
    @GetMapping("/{expenseId}/{memberId}")
    @Operation(summary = "지출 조회 API")
    @Parameters({
            @Parameter(name = "memberId", description = "사용자 ID, path variable 입니다!"),
            @Parameter(name = "expenseId", description = "지출 ID, path variable 입니다!")
    })
    public ApiResponse<ExpenseResponseDTO.GetExpenseResultDTO> getExpense(
            @PathVariable Long memberId,
            @PathVariable Long expenseId) {

        Expense expense = expenseQueryService.getExpense(memberId, expenseId);
        return ApiResponse.onSuccess(ExpenseConverter.toGetExpenseResultDTO(expense));
    }

    // 지출 모아보기
    @GetMapping
    @Operation(summary = "지출 리스트 조회 API")
    public ApiResponse<ExpenseResponseDTO.ExpensePreviewListResultDTO> getExpenseList(@RequestParam(name = "page") Integer page,
                                                                                @RequestParam(name = "memberId") Long memberId,
                                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expenseDate
    ){

        Page<Expense> expenseList = expenseQueryService.getExpenseList(page - 1, memberId, expenseDate);
        return ApiResponse.onSuccess(ExpenseConverter.toGetExpenseResultListDTO(expenseList));
    }

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

    // 지출 삭제
    @DeleteMapping("/{expenseId}/{memberId}")
    @Operation(summary = "지출 삭제 API")
    @Parameters({
            @Parameter(name = "expenseId", description = "지출 ID, path variable 입니다!"),
            @Parameter(name = "memberId", description = "사용자 ID, path variable 입니다!")
    })
    public ApiResponse<String> deleteExpense(
            @PathVariable("memberId") Long memberId,
            @PathVariable("expenseId") Long expenseId) {

        expenseQueryService.deleteExpense(memberId, expenseId);
        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    // 일별 지출 종 금액 리스트 조회 API
    @GetMapping("/dailyTotalExpense")
    @Operation(summary = "일별 지출 총 금액 리스트 조회 API")
    @Parameters({
            @Parameter(name = "memberId", description = "지출의 주인인 사용자 ID, path variable 입니다!"),
            @Parameter(name = "year", description = "조회할 연도 (ex. 2025)", required = true),
            @Parameter(name = "month", description = "조회할 월 (1~12)", required = true)
    })
    public ApiResponse<ExpenseResponseDTO.GetDailyExpenseResultDTOList> getDailyExpense(
            @RequestParam(name = "memberId") Long memberId,
            @RequestParam int year,
            @RequestParam int month) {

        List<Expense> expenseList =
                expenseQueryService.getDailyExpense(memberId, year, month);

        System.out.println("DEBUG: toGetDailyExpenseResultDTO 시작");
        System.out.println("expenseList size: " + expenseList.size());
        System.out.println("DEBUG: toGetDailyExpenseResultDTO 끝");

        return ApiResponse.onSuccess(ExpenseConverter.toGetDailyExpenseResultDTO(expenseList, memberId));
    }
}
