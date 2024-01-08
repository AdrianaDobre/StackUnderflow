package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.*;
import com.stackunderflow.backend.Exception.AlreadyExistsException;
import com.stackunderflow.backend.Exception.BadRegisterOrLoginException;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.*;
import com.stackunderflow.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserXBadgeRepository userXBadgeRepository;
    private final VoteRepository voteRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostXTopicRepository postXTopicRepository;

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

    @Override
    public UserDTO getUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFound("User not found"));
        List<Badge> badges = userXBadgeRepository.findUserXBadgeByUserId(id).stream().map(object -> object.getId().getBadge()).toList();
        List<Vote> votes = voteRepository.getVotesByUserId(id).stream().toList();

        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .points(user.getPoints())
                .badges(badges)
                .votes(votes).build();
    }

    @Override
    public Message editUser(EditUserDTO userDTO, Long id, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(id, user.getId())){
            throw new ForbiddenActionException("Cannot edit another user profile");
        }

        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(user);

        return new Message("Profile edited successfully");
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

    @Override
    public Message deleteUser(Long id, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(id, user.getId())){
            throw new ForbiddenActionException("Cannot delete another user account");
        }

        voteRepository.deleteVoteByUserId(id);
        postXTopicRepository.deleteAllInBatch(postXTopicRepository.findPostXTopicByUserId(id));
        userXBadgeRepository.deleteAllInBatch(userXBadgeRepository.findUserXBadgeByUserId(id));

        userRepository.delete(user);
        return new Message("User deleted successfully");
    }
}
