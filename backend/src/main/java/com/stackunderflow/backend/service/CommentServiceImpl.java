package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.model.Vote;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.PostRepository;
import com.stackunderflow.backend.repository.UserRepository;
import com.stackunderflow.backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    @Override
    public void saveComment(SaveCommentDTO comment) {
        Comment newComment = Comment.builder()
                .user(userRepository.findById(comment.getUserId()).orElseThrow(() -> new ObjectNotFound("User not found")))
                .post(postRepository.findById(comment.getPostId()).orElseThrow(() -> new ObjectNotFound("Post not found")))
                .text(comment.getText())
                .isTheBest(false)
                .date(LocalDateTime.now()).build();
        commentRepository.save(newComment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Post not found"));
        List<Vote> votes = voteRepository.getVotesByCommentId(id);
        Long upVoteCount = (long) votes.stream().filter(Vote::getVoteType).toList().size();
        Long downVoteCount = (long) votes.stream().filter(vote -> !vote.getVoteType()).toList().size();
        return CommentDTO.builder()
                .body(comment.getText())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .upVoteCount(upVoteCount)
                .downVoteCount(downVoteCount)
                .date(comment.getDate())
                .build();
    }
}
