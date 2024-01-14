package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.*;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    ResponseEntity<Message> editUser(@RequestBody EditUserDTO user, @PathVariable Long id, Principal principal){
        return new ResponseEntity<>(userService.editUser(user,id,principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    ResponseEntity<Message> deleteUser(@PathVariable Long id, Principal principal){
        return new ResponseEntity<>(userService.deleteUser(id, principal.getName()), HttpStatus.OK);
    }
}
