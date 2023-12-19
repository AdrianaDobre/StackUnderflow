package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge,Long> {
}
