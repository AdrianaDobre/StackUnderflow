package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.model.Badge;
import com.stackunderflow.backend.service.BadgeService;
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
@RequestMapping("/api/badge")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    void saveBadge(@RequestBody Badge badge){
        badgeService.saveBadge(badge);
    }

    @GetMapping("/all")
    ResponseEntity<List<Badge>> getAllBadges(){
        return new ResponseEntity<>(badgeService.getAllBadges(), HttpStatus.OK);
    }
}
