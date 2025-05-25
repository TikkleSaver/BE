package com.tikklesaver.domain.Expense.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.Expense.entity.Expense;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import com.tikklesaver.domain.Expense.entity.QExpense;
import com.tikklesaver.domain.Expense.entity.QExpenseComment;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExpenseCommentRepositoryImpl implements ExpenseCommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = QMember.member;
    private final QExpenseComment qExpenseComment = QExpenseComment.expenseComment;

    // 지출 피드백 삭제를 위한 데이터 조회
    @Override
    public Optional<ExpenseComment> findByCommenterIdAndExpenseCommentId(Long commenterId, Long expenseCommentId) {
        ExpenseComment result = jpaQueryFactory
                .selectFrom(qExpenseComment)
                .join(qExpenseComment.commenter, qMember).fetchJoin()
                .where(
                        qExpenseComment.id.eq(expenseCommentId),
                        qMember.id.eq(commenterId)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
