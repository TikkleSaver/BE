package com.tikklesaver.domain.Expense.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.entity.QExpense;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QExpense qExpense = QExpense.expense;
    private final QMember qMember = QMember.member;

    // 특정 사용자가 지출한 특정 지출 조회
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
}
