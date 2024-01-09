package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.*;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
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

    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    Message editUser(@RequestBody EditUserDTO user, @PathVariable Long id, Principal principal){
        return userService.editUser(user,id,principal.getName());
    }

    @DeleteMapping("/{id}")
    Message deleteUser(@PathVariable Long id, Principal principal){
        return userService.deleteUser(id, principal.getName());
    }
}
