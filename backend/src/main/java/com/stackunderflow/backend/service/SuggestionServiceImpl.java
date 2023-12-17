package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.SaveSuggestionDTO;
import com.stackunderflow.backend.model.Suggestion;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.SuggestionRepository;
import com.stackunderflow.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService{
    private final SuggestionRepository suggestionRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void saveSuggestion(SaveSuggestionDTO saveSuggestionDTO) {
        suggestionRepository.save(
                Suggestion.builder()
                        .user(userRepository.findById(saveSuggestionDTO.getUserId()).get())
                        .comment(commentRepository.findById(saveSuggestionDTO.getCommentId()).get())
                        .text(saveSuggestionDTO.getText())
                        .date(LocalDateTime.now()).build());
    }

    @Override
    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }
}
