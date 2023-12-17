package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.SaveSuggestionDTO;
import com.stackunderflow.backend.model.Suggestion;

import java.util.List;

public interface SuggestionService {
    void saveSuggestion(SaveSuggestionDTO saveSuggestionDTO);
    List<Suggestion> getAllSuggestions();
}
