package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.PostDTO;
import com.stackunderflow.backend.DTOS.SaveSuggestionDTO;
import com.stackunderflow.backend.DTOS.SuggestionDTO;
import com.stackunderflow.backend.model.Badge;
import com.stackunderflow.backend.model.Suggestion;
import com.stackunderflow.backend.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/suggestion")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;

    @PostMapping
    void saveSuggestion(@RequestBody SaveSuggestionDTO saveSuggestionDTO, Principal principal){
        suggestionService.saveSuggestion(saveSuggestionDTO,principal.getName());
    }

    @GetMapping("/all")
    List<Suggestion> getAllSuggestions(){
        return suggestionService.getAllSuggestions();
    }

    @GetMapping("/{id}")
    SuggestionDTO getSuggestionById(@PathVariable Long id){
        return suggestionService.getSuggestionById(id);
    }

    @DeleteMapping("/{id}")
    Message deleteSuggestion(@PathVariable Long id, Principal principal) throws BadRequestException {
        return suggestionService.deleteSuggestion(id,principal.getName());
    }
}
