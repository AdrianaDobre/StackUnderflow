package com.stackunderflow.backend.service;

import com.stackunderflow.backend.model.Badge;
import com.stackunderflow.backend.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService{
    private final BadgeRepository badgeRepository;

    @Override
    public void saveBadge(Badge badge) {
        badgeRepository.save(badge);
    }

    @Override
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }
}
