package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.SaveSuggestionDTO;
import com.stackunderflow.backend.model.Badge;
import com.stackunderflow.backend.model.Suggestion;
import com.stackunderflow.backend.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/suggestion")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;

    @PostMapping("/save")
    void saveSuggestion(@RequestBody SaveSuggestionDTO saveSuggestionDTO){
        suggestionService.saveSuggestion(saveSuggestionDTO);
    }

    @GetMapping("/all")
    List<Suggestion> getAllSuggestions(){
        return suggestionService.getAllSuggestions();
    }
}
