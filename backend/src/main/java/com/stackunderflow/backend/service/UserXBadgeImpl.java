package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.AddBadgeToUserDTO;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.Badge;
import com.stackunderflow.backend.model.UserXBadge;
import com.stackunderflow.backend.model.UserXBadgeId;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.repository.BadgeRepository;
import com.stackunderflow.backend.repository.UserRepository;
import com.stackunderflow.backend.repository.UserXBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserXBadgeImpl implements UserXBadgeService{
    private final UserXBadgeRepository userXBadgeRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public void saveUserXBadge(AddBadgeToUserDTO addBadgeToUserDTO) {
        Users user = userRepository.findById(addBadgeToUserDTO.getUserId()).orElseThrow(() -> new ObjectNotFound("User not found"));
        Badge badge = badgeRepository.findById(addBadgeToUserDTO.getBadgeId()).orElseThrow(() -> new ObjectNotFound("Badge not found"));
        UserXBadgeId id = new UserXBadgeId(user,badge);
        userXBadgeRepository.save(new UserXBadge(id,addBadgeToUserDTO.getDate()));
    }

    @Override
    public List<UserXBadge> getAllUserXBadges() {
        return userXBadgeRepository.findAll();
    }
}
