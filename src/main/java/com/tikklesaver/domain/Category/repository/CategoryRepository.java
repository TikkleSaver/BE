package com.tikklesaver.domain.Category.repository;

import com.tikklesaver.domain.Category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
