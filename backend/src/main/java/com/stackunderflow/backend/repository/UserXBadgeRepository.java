package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.model.UserXBadge;
import com.stackunderflow.backend.model.UserXBadgeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserXBadgeRepository extends JpaRepository<UserXBadge, UserXBadgeId> {
    @Query("select ub from UserXBadge ub where ub.id.user.id = :userId")
    List<UserXBadge> findUserXBadgeByUserId(Long userId);
}
