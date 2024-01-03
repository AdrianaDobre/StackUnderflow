package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.RegisterRequestAuth;
import com.stackunderflow.backend.Exception.AlreadyExistsException;
import com.stackunderflow.backend.Exception.BadRegisterOrLoginException;
import com.stackunderflow.backend.model.Role;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void registerUser(RegisterRequestAuth registerRequestAuth) {
        Optional<Users> user = userRepository.findByEmail(registerRequestAuth.getEmail());
        if (user.isPresent()) {
            throw new AlreadyExistsException("A user with email " + registerRequestAuth.getEmail() + " already exists");
        }
        if (registerRequestAuth.getPassword().length() < 9 || !meetsPasswordAllPatterns(registerRequestAuth.getPassword())){
            throw new BadRegisterOrLoginException("Password does not meet security requirements!");
        }
        if (!Objects.equals(registerRequestAuth.getPassword(), registerRequestAuth.getRetypePassword())){
            throw new BadRegisterOrLoginException("The passwords do not match!");
        }
        userRepository.save(Users.builder()
                .username(registerRequestAuth.getUsername())
                .email(registerRequestAuth.getEmail())
                .role(Role.USER)
                .points(0.d)
                .password(passwordEncoder.encode(registerRequestAuth.getPassword())).build());
        log.info("Account created");
    }

    private boolean meetsPasswordAllPatterns(String password){
        Pattern lowercasePattern = Pattern.compile(".*[a-z].*");
        Pattern uppercasePattern = Pattern.compile(".*[A-Z].*");
        Pattern digitPattern = Pattern.compile(".*\\d.*");
        Pattern specialCharPattern = Pattern.compile(".*[!@#$%^&*()_+\\-=[{]}\\\\|;:'\",<.>/?].*");

        return lowercasePattern.matcher(password).matches() &&
                uppercasePattern.matcher(password).matches() &&
                digitPattern.matcher(password).matches() &&
                specialCharPattern.matcher(password).matches();
    }
}
