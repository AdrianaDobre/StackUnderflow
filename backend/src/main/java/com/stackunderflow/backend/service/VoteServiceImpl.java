package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.AddVote;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.model.Vote;
import com.stackunderflow.backend.model.VoteId;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.UserRepository;
import com.stackunderflow.backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void saveVote(AddVote addVote) {
        Users user = userRepository.findById(addVote.getUserId()).orElseThrow(() -> new ObjectNotFound("User not found"));
        Comment comment = commentRepository.findById(addVote.getCommentId()).orElseThrow(() -> new ObjectNotFound("Comment not found"));
        VoteId id = new VoteId(comment,user);
        voteRepository.save(new Vote(id, addVote.getVoteType()));
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }
}
