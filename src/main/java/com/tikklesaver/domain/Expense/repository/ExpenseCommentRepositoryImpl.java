package com.tikklesaver.domain.Expense.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import com.tikklesaver.domain.Expense.entity.QExpenseComment;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

    // 지출 피드백 리스트 조회
    @Override
    public Page<ExpenseComment> findAll(Pageable pageable, Long memberId, Date expenseDate){
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

        List<ExpenseComment> result = jpaQueryFactory
                .selectFrom(qExpenseComment)
                .join(qExpenseComment.member, qMember).fetchJoin()
                .where(
                        qMember.id.eq(memberId),
                        qExpenseComment.expenseDate.between(startOfDay, endOfDay)
                )
                .orderBy(qExpenseComment.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(qExpenseComment.count())
                .from(qExpenseComment)
                .join(qExpenseComment.member, qMember)
                .where(
                        qMember.id.eq(memberId),
                        qExpenseComment.expenseDate.between(startOfDay, endOfDay)
                )
                .fetchOne();

        if (total == null) total = 0L;

        return new PageImpl<>(result, pageable, total);
    }
}
