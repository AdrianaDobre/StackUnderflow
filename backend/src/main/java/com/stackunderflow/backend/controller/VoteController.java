package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.AddVote;
import com.stackunderflow.backend.model.Vote;
import com.stackunderflow.backend.service.VoteService;
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
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/save")
    void saveVote(@RequestBody AddVote addVote){
        voteService.saveVote(addVote);
    }

    @GetMapping("/all")
    List<Vote> getAllBadges() {
        return voteService.getAllVotes();
    }
}
