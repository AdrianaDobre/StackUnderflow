package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.AddBadgeToUserDTO;
import com.stackunderflow.backend.model.Badge;
import com.stackunderflow.backend.model.UserXBadge;
import com.stackunderflow.backend.service.UserXBadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/userXBadge")
@RequiredArgsConstructor
public class UserXBadgeController {
    private final UserXBadgeService userXBadgeService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    void saveUserXBadge(@RequestBody AddBadgeToUserDTO addBadgeToUserDTO){
        userXBadgeService.saveUserXBadge(addBadgeToUserDTO);
    }

    @GetMapping("/all")
    ResponseEntity<List<UserXBadge>> getAllUserXBadges(){
        return new ResponseEntity<>(userXBadgeService.getAllUserXBadges(), HttpStatus.OK);
    }
}
