package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.Exception.AlreadyExistsException;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.*;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.PostRepository;
import com.stackunderflow.backend.repository.UserRepository;
import com.stackunderflow.backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Message likeComment(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFound("User not found"));

        VoteId voteId = new VoteId(comment, user);

        Optional<Vote> oldVote = voteRepository.findById(voteId);
        if(oldVote.isPresent()){
            if(oldVote.get().getVoteType()){
                throw new AlreadyExistsException("Answer liked already");
            }
            else{
                voteRepository.deleteById(voteId);
            }
        }

        voteRepository.save(new Vote(voteId,true));
        user.setPoints(user.getPoints() + 1);
        userRepository.save(user);
        return new Message("Answer liked successfully");
    }

    @Override
    public Message dislikeComment(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFound("User not found"));

        VoteId voteId = new VoteId(comment, user);

        Optional<Vote> oldVote = voteRepository.findById(voteId);
        if(oldVote.isPresent()){
            if(oldVote.get().getVoteType()){
                voteRepository.deleteById(voteId);
            }
            else{
                throw new AlreadyExistsException("Answer disliked already");
            }
        }

        voteRepository.save(new Vote(voteId,false));
        return new Message("Answer disliked successfully");
    }

    @Override
    public Message deleteLikeOrDislike(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFound("User not found"));

        VoteId voteId = new VoteId(comment, user);

        Optional<Vote> oldVote = voteRepository.findById(voteId);
        if(oldVote.isPresent()){
            if(oldVote.get().getVoteType()){
                user.setPoints(user.getPoints() - 1);
                userRepository.save(user);
            }
            voteRepository.deleteById(voteId);
            return new Message("Like/Dislike deleted successfully");
        }
        else{
            throw new ObjectNotFound("Like/Dislike not found");
        }
    }
}
