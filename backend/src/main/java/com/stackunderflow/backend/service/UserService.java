package com.stackunderflow.backend.service;

import com.stackunderflow.backend.model.Users;

import java.util.List;

public interface UserService {
    void saveUser(Users user);
    List<Users> getAllUsers();
}
