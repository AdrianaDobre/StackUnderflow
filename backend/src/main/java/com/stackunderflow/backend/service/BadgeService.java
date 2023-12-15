package com.stackunderflow.backend.service;

import com.stackunderflow.backend.model.Badge;

import java.util.List;

public interface BadgeService {
    void saveBadge(Badge badge);
    List<Badge> getAllBadges();
}
