package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.AddBadgeToUserDTO;
import com.stackunderflow.backend.model.UserXBadge;

import java.util.List;

public interface UserXBadgeService {
    void saveUserXBadge(AddBadgeToUserDTO addBadgeToUserDTO);
    List<UserXBadge> getAllUserXBadges();
}
