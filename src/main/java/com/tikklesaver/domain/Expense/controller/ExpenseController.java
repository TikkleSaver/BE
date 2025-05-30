package com.tikklesaver.domain.Expense.controller;

import com.tikklesaver.domain.Expense.converter.ExpenseConverter;
import com.tikklesaver.domain.Expense.dto.ExpenseRequestDTO;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.service.ExpenseCommandService;
import com.tikklesaver.domain.Expense.service.ExpenseQueryService;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.annotation.CurrentMember;
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
    @GetMapping("/{expenseId}")
    @Operation(summary = "지출 조회 API")
    @Parameters({
            @Parameter(name = "expenseId", description = "지출 ID, path variable 입니다!")
    })
    public ApiResponse<ExpenseResponseDTO.GetExpenseResultDTO> getExpense(
            @CurrentMember Member member,
            @PathVariable Long expenseId) {

        Expense expense = expenseQueryService.getExpense(member.getId(), expenseId);
        return ApiResponse.onSuccess(ExpenseConverter.toGetExpenseResultDTO(expense));
    }

    // 지출 모아보기
    @GetMapping
    @Operation(summary = "지출 리스트 조회 API")
    public ApiResponse<ExpenseResponseDTO.ExpensePreviewListResultDTO> getExpenseList(
            @CurrentMember Member viewer,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "memberId") Long memberId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expenseDate
    ){

        Page<Expense> expenseList = expenseQueryService.getExpenseList(viewer, page - 1, memberId, expenseDate);
        return ApiResponse.onSuccess(ExpenseConverter.toGetExpenseResultListDTO(expenseList));
    }

    // 지출 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지출 생성 API")
    public ApiResponse<ExpenseResponseDTO.CreateExpenseResultDTO> addExpense(
            @CurrentMember Member member,
            @RequestPart("request") @Valid ExpenseRequestDTO.CreateExpenseRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Expense expense = expenseCommandService.addExpense(member.getId(), request, file);
        return ApiResponse.onSuccess(ExpenseConverter.toExpenseResultDTO(expense));
    }

    // 지출 수정
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지출 수정 API")
    public ApiResponse<ExpenseResponseDTO.UpdateExpenseResultDTO> updateExpense(
            @CurrentMember Member member,
            @RequestPart("request") @Valid ExpenseRequestDTO.UpdateExpenseRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Expense expense = expenseQueryService.updateExpense(member.getId(), request, file);
        return ApiResponse.onSuccess(ExpenseConverter.toUpdateExpenseResultDTO(expense));
    }

    // 지출 삭제
    @DeleteMapping("/{expenseId}")
    @Operation(summary = "지출 삭제 API")
    @Parameters({
            @Parameter(name = "expenseId", description = "지출 ID, path variable 입니다!")
    })
    public ApiResponse<String> deleteExpense(
            @CurrentMember Member member,
            @PathVariable("expenseId") Long expenseId) {

        expenseQueryService.deleteExpense(member.getId(), expenseId);
        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    // 일별 지출 종 금액 리스트 조회 API
    @GetMapping("/dailyTotalExpense")
    @Operation(summary = "일별 지출 총 금액 리스트 조회 API")
    @Parameters({
            @Parameter(name = "memberId", description = "지출의 주인인 사용자 ID, path variable 입니다!", required = false),
            @Parameter(name = "year", description = "조회할 연도 (ex. 2025)", required = true),
            @Parameter(name = "month", description = "조회할 월 (1~12)", required = true)
    })
    public ApiResponse<ExpenseResponseDTO.GetDailyExpenseResultDTOList> getDailyExpense(
            @CurrentMember Member viewer,
            @RequestParam(value = "memberId", required = false) Long memberId,
            @RequestParam int year,
            @RequestParam int month) {

        if(memberId == null) {
            List<Expense> expenseList =
                    expenseQueryService.getDailyExpense(viewer, viewer.getId(), year, month);

            return ApiResponse.onSuccess(ExpenseConverter.toGetDailyExpenseResultDTO(expenseList, viewer.getId()));
        }
        else {
            List<Expense> expenseList =
                    expenseQueryService.getDailyExpense(viewer, memberId, year, month);

            return ApiResponse.onSuccess(ExpenseConverter.toGetDailyExpenseResultDTO(expenseList, memberId));
        }
    }

    // 특정 년도의 월별 지출 총 금액 리스트 조회 API
    @GetMapping("/monthlyTotalExpense")
    @Operation(summary = "특정 년도의 월별 지출 총 금액 리스트 조회 API")
    @Parameters({
            @Parameter(name = "year", description = "조회할 연도 (ex. 2025)", required = true)
    })
    public ApiResponse<ExpenseResponseDTO.GetMonthlyExpenseResultDTOList> getMonthlyExpense(
            @CurrentMember Member member,
            @RequestParam int year) {

        List<ExpenseResponseDTO.MonthlyExpenseTotalDTO> expenseList =
                expenseQueryService.getMonthlyExpense(member.getId(), year);

        return ApiResponse.onSuccess(ExpenseConverter.toGetMonthlyExpenseResultDTO(expenseList, member.getId()));
    }

    // 특정 사용자의 특정 달의 카테고리별 지출 금액 리스트 조회
    @GetMapping("/month/category/totalExpense")
    @Operation(summary = "특정 사용자의 특정 달의 카테고리별 지출 금액 리스트 조회 API")
    @Parameters({
            @Parameter(name = "year", description = "조회할 연도 (ex. 2025)", required = true),
            @Parameter(name = "month", description = "조회할 월 (1~12)", required = true)
    })
    public ApiResponse<ExpenseResponseDTO.GetTotalExpenseByCategoryResultDTOList> getTotalExpenseByCategory(
            @CurrentMember Member member,
            @RequestParam int year,
            @RequestParam int month) {

        List<ExpenseResponseDTO.TotalExpenseByCategoryResultDTO> expenseList =
                expenseQueryService.getTotalExpenseByCategory(member.getId(), year, month);

        return ApiResponse.onSuccess(ExpenseConverter.toGetgetTotalExpenseByCategoryResultDTO(member.getId(), year, month, expenseList));
    }

    // 특정 사용자의 특정 달 지출 총 금액 조회
    @GetMapping("/month/totalExpense")
    @Operation(summary = "특정 사용자의 특정 달 지출 총 금액 조회 API")
    @Parameters({
            @Parameter(name = "year", description = "조회할 연도 (ex. 2025)", required = true),
            @Parameter(name = "month", description = "조회할 월 (1~12)", required = true)
    })
    public ApiResponse<ExpenseResponseDTO.GetTotalExpenseByMonthResultDTO> getTotalExpenseByMonth(
            @CurrentMember Member member,
            @RequestParam int year,
            @RequestParam int month) {

        Long totalAmount = expenseQueryService.getTotalExpenseByMonth(member.getId(), year, month);

        return ApiResponse.onSuccess(ExpenseConverter.toGetTotalExpenseByMonthResultDTO(member.getId(), year, month, totalAmount));
    }

    // 특정 사용자의 특정 달 지출 TOP3 카테고리 조회
    @GetMapping("/month/category/top3")
    @Operation(summary = "특정 사용자의 특정 달 지출 TOP3 카테고리 조회 API")
    @Parameters({
            @Parameter(name = "year", description = "조회할 연도 (ex. 2025)", required = true),
            @Parameter(name = "month", description = "조회할 월 (1~12)", required = true)
    })
    public ApiResponse<ExpenseResponseDTO.GetExpenseTop3CategoryResultDTO> getTotalExpenseTop3Category(
            @CurrentMember Member member,
            @RequestParam int year,
            @RequestParam int month) {

        ExpenseResponseDTO.GetExpenseTop3CategoryResultDTO result = expenseQueryService.getTotalExpenseTop3Category(member.getId(), year, month);

        return ApiResponse.onSuccess(ExpenseConverter.toGetTotalExpenseTop3Category(result));
    }

}
