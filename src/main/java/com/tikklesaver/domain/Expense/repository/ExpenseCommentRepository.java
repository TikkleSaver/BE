package com.tikklesaver.domain.Expense.repository;

import com.tikklesaver.domain.Expense.entity.ExpenseComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCommentRepository extends JpaRepository<ExpenseComment, Long> {
}
