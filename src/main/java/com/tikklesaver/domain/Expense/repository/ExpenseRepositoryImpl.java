package com.tikklesaver.domain.Expense.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.Category.entity.QCategory;
import com.tikklesaver.domain.Expense.dto.ExpenseResponseDTO;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.entity.QExpense;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QExpense qExpense = QExpense.expense;
    private final QMember qMember = QMember.member;
    private final QCategory qCategory = QCategory.category;

    // 특정 사용자가 지출한 특정 지출 조회
    @Override
    public Optional<Expense> findByMemberIdAndExpenseId(Long memberId, Long expenseId){
        Expense result = jpaQueryFactory
                .selectFrom(qExpense)
                .join(qExpense.member, qMember).fetchJoin()
                .where(
                        qExpense.id.eq(expenseId),
                        qMember.id.eq(memberId)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    // 지출 리스트 조회
    @Override
    public Page<Expense> findAll(Pageable pageable,
                                                      Long memberId,
                                                      Date expenseDate) {
        // 날짜의 00:00:00 ~ 23:59:59 범위 계산
        Calendar cal = Calendar.getInstance();
        cal.setTime(expenseDate);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date endOfDay = cal.getTime();

        List<Expense> content = jpaQueryFactory
                .selectFrom(qExpense)
                .join(qExpense.member, qMember).fetchJoin()
                .where(
                        qMember.id.eq(memberId),
                        qExpense.expenseDate.between(startOfDay, endOfDay)
                )
                .orderBy(qExpense.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(qExpense.count())
                .from(qExpense)
                .join(qExpense.member, qMember)
                .where(
                        qMember.id.eq(memberId),
                        qExpense.expenseDate.between(startOfDay, endOfDay)
                )
                .fetchOne();

        if (total == null) total = 0L;

        return new PageImpl<>(content, pageable, total);
    }

    // 일별 지출 총액 리스트 조회
    @Override
    public List<Expense> findDailyExpenseTotalByMemberIdAndYearMonth(Long memberId, int year, int month) {
        // 조회할 연도의 해당 월 1일
        LocalDate startLocalDate = LocalDate.of(year, month, 1);
        // 다음 달 1일 (조회 종료일 + 1)
        LocalDate nextMonthStartLocalDate = startLocalDate.plusMonths(1);

        // java.sql.Date 타입 변환
        Date startDate = java.sql.Date.valueOf(startLocalDate);
        Date nextMonthStartDate = java.sql.Date.valueOf(nextMonthStartLocalDate);

        return jpaQueryFactory
                .selectFrom(qExpense)
                // INNER JOIN 대신 LEFT JOIN으로 바꿔서 데이터 누락 체크 가능
                .leftJoin(qExpense.member, qMember).fetchJoin()
                .where(
                        qMember.id.eq(memberId),
                        qExpense.expenseDate.goe(startDate),   // >= startDate
                        qExpense.expenseDate.lt(nextMonthStartDate) // < 다음 달 1일
                )
                .orderBy(qExpense.expenseDate.asc())
                .fetch();
    }

    // 특정 년도의 월별 지출 총 금액 리스트 조회
    @Override
    public List<ExpenseResponseDTO.MonthlyExpenseTotalDTO> findMonthlyExpenseTotalByMemberIdAndYear(Long memberId, int year) {
        QExpense expense = QExpense.expense;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ExpenseResponseDTO.MonthlyExpenseTotalDTO.class,
                        Expressions.numberTemplate(Integer.class, "MONTH({0})", expense.expenseDate).as("month"),
                        expense.cost.sum().as("totalAmount")
                ))
                .from(expense)
                .where(
                        expense.member.id.eq(memberId),
                        expense.expenseDate.between(
                                java.sql.Date.valueOf(LocalDate.of(year, 1, 1)),
                                java.sql.Date.valueOf(LocalDate.of(year, 12, 31))
                        )
                )
                .groupBy(Expressions.numberTemplate(Integer.class, "MONTH({0})", expense.expenseDate))
                .orderBy(Expressions.numberTemplate(Integer.class, "MONTH({0})", expense.expenseDate).asc())
                .fetch();
    }

    // 특정 사용자의 특정 달의 카테고리별 지출 금액 리스트 조회
    @Override
    public List<ExpenseResponseDTO.TotalExpenseByCategoryResultDTO> findExpenseTotalByMemberIdAndYearMonthCategory(Long memberId, int year, int month){
        LocalDate startLocalDate = LocalDate.of(year, month, 1);
        LocalDate nextMonthStartLocalDate = startLocalDate.plusMonths(1);

        Date startDate = java.sql.Date.valueOf(startLocalDate);
        Date nextMonthStartDate = java.sql.Date.valueOf(nextMonthStartLocalDate);

        return jpaQueryFactory
                .select(Projections.constructor(
                        ExpenseResponseDTO.TotalExpenseByCategoryResultDTO.class,
                        qCategory.id,
                        qExpense.cost.sum().coalesce(0L)  // 지출이 없을 경우 0으로 처리
                ))
                .from(qCategory)
                .leftJoin(qExpense)
                .on(qExpense.category.id.eq(qCategory.id)
                        .and(qExpense.member.id.eq(memberId))
                        .and(qExpense.expenseDate.goe(startDate))
                        .and(qExpense.expenseDate.lt(nextMonthStartDate))
                )
                .groupBy(qCategory.id)
                .orderBy(qCategory.id.asc())  // 필요 시 정렬
                .fetch();
    }

    // 특정 사용자의 특정 달 지출 총 금액 조회
    @Override
    public Long findExpenseTotalByMemberIdAndYearMonth(Long memberId, int year, int month){
        LocalDate startLocalDate = LocalDate.of(year, month, 1);
        LocalDate nextMonthStartLocalDate = startLocalDate.plusMonths(1);

        Date startDate = java.sql.Date.valueOf(startLocalDate);
        Date nextMonthStartDate = java.sql.Date.valueOf(nextMonthStartLocalDate);

        Long total = jpaQueryFactory
                .select(qExpense.cost.sum())
                .from(qExpense)
                .where(
                        qExpense.member.id.eq(memberId),
                        qExpense.expenseDate.goe(startDate),
                        qExpense.expenseDate.lt(nextMonthStartDate)
                )
                .fetchOne();

        return total != null ? total : 0L;
    }

    // 특정 사용자의 특정 달 지출 TOP3 카테고리 조회
    @Override
    public ExpenseResponseDTO.GetExpenseTop3CategoryResultDTO findExpenseTop3Categories(Long memberId, int year, int month){
        LocalDate startLocalDate = LocalDate.of(year, month, 1);
        LocalDate nextMonthStartLocalDate = startLocalDate.plusMonths(1);

        Date startDate = java.sql.Date.valueOf(startLocalDate);
        Date nextMonthStartDate = java.sql.Date.valueOf(nextMonthStartLocalDate);

        List<Long> topCategories = jpaQueryFactory
                .select(qCategory.id)
                .from(qExpense)
                .join(qExpense.category, qCategory)
                .where(
                        qExpense.member.id.eq(memberId),
                        qExpense.expenseDate.goe(startDate),
                        qExpense.expenseDate.lt(nextMonthStartDate)
                )
                .groupBy(qCategory.id, qCategory.category_name)
                .orderBy(
                        qExpense.cost.sum().coalesce(0L).desc().nullsLast(),  // 금액 내림차순
                        qCategory.category_name.asc()                        // 이름 오름차순 (동점 처리용)
                )
                .limit(3)
                .fetch();

        Long category1 = (!topCategories.isEmpty()) ? topCategories.get(0) : 0;
        Long category2 = (topCategories.size() > 1) ? topCategories.get(1) : 0;
        Long category3 = (topCategories.size() > 2) ? topCategories.get(2) : 0;

        return ExpenseResponseDTO.GetExpenseTop3CategoryResultDTO.builder()
                .memberId(memberId)
                .category1(category1)
                .category2(category2)
                .category3(category3)
                .year(year)
                .month(month)
                .build();
    }
}
