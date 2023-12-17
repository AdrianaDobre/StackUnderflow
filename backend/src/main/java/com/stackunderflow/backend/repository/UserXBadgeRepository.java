package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.UserXBadge;
import com.stackunderflow.backend.model.UserXBadgeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserXBadgeRepository extends JpaRepository<UserXBadge, UserXBadgeId> {
}
