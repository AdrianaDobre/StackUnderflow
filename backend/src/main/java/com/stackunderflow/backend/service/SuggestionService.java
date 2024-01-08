package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveSuggestionDTO;
import com.stackunderflow.backend.DTOS.SuggestionDTO;
import com.stackunderflow.backend.model.Suggestion;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface SuggestionService {
    Message saveSuggestion(SaveSuggestionDTO saveSuggestionDTO, String email);
    List<Suggestion> getAllSuggestions();
    SuggestionDTO getSuggestionById(Long id);
    Message deleteSuggestion(Long id, String email) throws BadRequestException;
}
