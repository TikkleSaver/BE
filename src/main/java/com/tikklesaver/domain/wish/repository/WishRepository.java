package com.tikklesaver.domain.wish.repository;

import com.tikklesaver.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long>, WishRepositoryCustom {
}
