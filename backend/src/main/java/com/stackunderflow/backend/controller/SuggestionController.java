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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/suggestion")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    void saveSuggestion(@RequestBody SaveSuggestionDTO saveSuggestionDTO, Principal principal){
        suggestionService.saveSuggestion(saveSuggestionDTO,principal.getName());
    }

    @GetMapping("/all")
    ResponseEntity<List<Suggestion>> getAllSuggestions(){
        return new ResponseEntity<>(suggestionService.getAllSuggestions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<SuggestionDTO> getSuggestionById(@PathVariable Long id){
        return new ResponseEntity<>(suggestionService.getSuggestionById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Message> deleteSuggestion(@PathVariable Long id, Principal principal) throws BadRequestException {
        return new ResponseEntity<>(suggestionService.deleteSuggestion(id,principal.getName()), HttpStatus.OK);
    }
}
