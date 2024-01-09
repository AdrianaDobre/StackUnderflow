package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveSuggestionDTO;
import com.stackunderflow.backend.DTOS.SuggestionDTO;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.*;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.SuggestionRepository;
import com.stackunderflow.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService{
    private final SuggestionRepository suggestionRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public Message saveSuggestion(SaveSuggestionDTO saveSuggestionDTO, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Comment comment = commentRepository.findById(saveSuggestionDTO.getAnswerId()).orElseThrow(() -> new ObjectNotFound("Answer does not exist"));
        Suggestion newSuggestion = Suggestion.builder()
                .user(user)
                .comment(comment)
                .text(saveSuggestionDTO.getBody())
                .date(LocalDateTime.now()).build();
        suggestionRepository.save(newSuggestion);

        return new Message("Suggestion created successfully");
    }

    @Override
    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    @Override
    public SuggestionDTO getSuggestionById(Long id) {
        Suggestion suggestion = suggestionRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Suggestion not found"));
        return SuggestionDTO.builder()
                .body(suggestion.getText())
                .createdDate(suggestion.getDate())
                .accepted((suggestion.getAccepted()))
                .build();
    }

    @Override
    public Message deleteSuggestion(Long id, String email) throws BadRequestException {
        Suggestion suggestion = suggestionRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Suggestion not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(suggestion.getUser().getId(), user.getId())){
            throw new ForbiddenActionException("Cannot delete suggestion without ownership");
        }
        if(suggestion.getAccepted() != null && suggestion.getAccepted()){
            throw new BadRequestException("Cannot delete accepted suggestion");
        }

        suggestionRepository.delete(suggestion);
        return new Message("Suggestion deleted successfully");
    }
}
