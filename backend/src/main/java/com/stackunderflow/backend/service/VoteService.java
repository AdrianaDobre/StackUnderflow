package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.AddVote;
import com.stackunderflow.backend.model.Vote;

import java.util.List;

public interface VoteService {
    void saveVote(AddVote addVote);
    List<Vote> getAllVotes();
}
