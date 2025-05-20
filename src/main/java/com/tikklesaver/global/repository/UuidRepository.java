package com.tikklesaver.global.repository;

import com.tikklesaver.global.common.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
