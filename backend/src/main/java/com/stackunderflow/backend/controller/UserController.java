package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    void saveUser(@RequestBody Users user){
        userService.saveUser(user);
    }

    @GetMapping("/all")
    List<Users> getAllUsers(){
        return userService.getAllUsers();
    }
}
