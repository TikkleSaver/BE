package com.tikklesaver.domain.product.repository;

import com.tikklesaver.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
