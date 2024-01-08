package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.*;
import com.stackunderflow.backend.model.Users;

import java.util.List;

public interface UserService {
    void saveUser(Users user);
    List<Users> getAllUsers();
    void registerUser(RegisterRequestAuth registerRequestAuth);
    UserDTO getUserById(Long id);
    Message editUser(EditUserDTO user, Long id, String email);
    Message deleteUser(Long id, String email);
}
